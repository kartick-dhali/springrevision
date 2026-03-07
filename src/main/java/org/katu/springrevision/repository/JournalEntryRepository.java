package org.katu.springrevision.repository;

import org.bson.types.ObjectId;
import org.katu.springrevision.entity.JournalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntity, ObjectId> {

}
