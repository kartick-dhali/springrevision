package org.katu.springrevision.repository;

import org.katu.springrevision.entity.UserEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEntryImpl {

    private final MongoTemplate mongoTemplate;

    public UserEntryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    public List<UserEntity> getUsersWithSA(){
        Query query = Query.query(
                Criteria.where("email")
                        .regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        ).addCriteria(
                Criteria.where("isSentimentAnalysisEnabled").is(true)
        );
        return mongoTemplate.find(query, UserEntity.class);

    }
}
