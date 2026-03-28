package org.katu.springrevision.schedular;

import org.junit.jupiter.api.Test;
import org.katu.springrevision.scheduler.UserSchedular;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedularTests {
    @Autowired
    private UserSchedular userSchedular;

    @Test
    void testSendEmailWithKafka(){
        userSchedular.getSentimentAndSendMail();
    }
}
