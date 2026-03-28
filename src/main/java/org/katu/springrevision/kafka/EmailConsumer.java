package org.katu.springrevision.kafka;

import lombok.extern.slf4j.Slf4j;
import org.katu.springrevision.DTO.EmailEvent;
import org.katu.springrevision.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }
    @KafkaListener(topics = "email-topic", groupId = "email-group")
    public void consume(EmailEvent emailEvent){
        //testing purpose
        if (emailEvent.getTo().contains("fail")) {
            throw new RuntimeException("Email sending failed!");
        }
        emailService.sendEmail(
                emailEvent.getTo(),
                emailEvent.getSubject(),
                emailEvent.getBody()
        );
        log.info("Email sent to {}", emailEvent.getTo());
    }
    //move this topic to dlq topic
    @KafkaListener(topics = "email-topic-dlq", groupId = "dlq-group")
    public void consumeDLQ(EmailEvent event) {
        log.info("DLQ Message: {}" , event);
    }
}
