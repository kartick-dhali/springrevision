package org.katu.springrevision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableTransactionManagement
@EnableKafka
//@EnableScheduling
public class SpringrevisionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringrevisionApplication.class, args);
    }

    @Bean
    public TransactionManager getTransactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }
    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }
}
