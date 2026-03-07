package org.katu.springrevision.repository;

import org.bson.types.ObjectId;
import org.katu.springrevision.entity.JournalEntity;
import org.katu.springrevision.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface UserEntryRepository extends MongoRepository<UserEntity, ObjectId> {

    UserEntity findByUserName(String userName);
    void deleteByUserName(String userName);
}
