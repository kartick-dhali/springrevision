package org.katu.springrevision.kafka;

import org.katu.springrevision.DTO.EmailEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    private final KafkaTemplate<String, EmailEvent> kafkaTemplate;

    public EmailProducer(KafkaTemplate<String, EmailEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendEmailEvent(EmailEvent event) {
        kafkaTemplate.send("email-topic",event.getTo(), event);
    }
}
