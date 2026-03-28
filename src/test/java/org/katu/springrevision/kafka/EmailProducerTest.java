package org.katu.springrevision.kafka;

import org.junit.jupiter.api.Test;
import org.katu.springrevision.DTO.EmailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailProducerTest {

    @Autowired
    private EmailProducer producer;

    @Test
    void testProuceEmailWithKafka(){
        EmailEvent event = new EmailEvent("fail@test.com", "Test", "This is a test email subjecet");
        producer.sendEmailEvent(event);
    }
}
