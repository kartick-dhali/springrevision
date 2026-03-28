package org.katu.springrevision.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class EmailServiceTests {
    @Autowired
    private EmailService emailService;

//    @Test
    void testSendEmail(){
        assertDoesNotThrow(() ->
                emailService.sendEmail(
                        "domisi8273@3dkai.com",
                        "This is a demo mail",
                        "Hello World! Nice to see you."
                )
        );
    }
}
