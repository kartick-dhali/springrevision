package org.katu.springrevision.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.katu.springrevision.DTO.EmailEvent;
import org.katu.springrevision.entity.JournalEntity;
import org.katu.springrevision.entity.UserEntity;
import org.katu.springrevision.kafka.EmailProducer;
import org.katu.springrevision.repository.UserEntryImpl;
import org.katu.springrevision.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Component
public class UserSchedular {

    private  final EmailService emailService;
    private final UserEntryImpl userEntryImpl;
    private final EmailProducer emailProducer;
    public UserSchedular(EmailService emailService, UserEntryImpl userEntryImpl, EmailProducer emailProducer) {
        this.emailService = emailService;
        this.userEntryImpl = userEntryImpl;
        this.emailProducer = emailProducer;
    }

//    @Scheduled(cron = "*/5 * * * * *")
    @Scheduled(cron = "0 0 0 * * SUN")
    public void getSentimentAndSendMail() {
        List<UserEntity> users = userEntryImpl.getUsersWithSA();
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        for (UserEntity user : users) {
            List<JournalEntity> journals = user.getJournals()
                    .stream()
                    .filter(journal -> journal.getJournalDate() != null &&
                            journal.getJournalDate().isAfter(sevenDaysAgo))
                    .toList();
            if (!journals.isEmpty()) {

                StringBuilder body = new StringBuilder();
                body.append("Hello ").append(user.getUserName()).append(",\n\n");
                body.append("Here are your journals from the last 7 days:\n\n");

                for (JournalEntity journal : journals) {
                    body.append("Title: ").append(journal.getJournalName()).append("\n");
                    body.append("Content: ").append(journal.getJournalContent()).append("\n");
                    body.append("Date: ").append(journal.getJournalDate()).append("\n\n");
                }


                emailProducer.sendEmailEvent(
                        new EmailEvent(
                                user.getEmail(),
                                "Your Weekly Journal Summary",
                                body.toString()
                        )
                );

                log.info("Email event pushed to Kafka for user {}", user.getUserName());
            }
        }


    }
}
