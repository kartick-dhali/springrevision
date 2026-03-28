package org.katu.springrevision.repository;


import org.katu.springrevision.config.ApiConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiConfigRepository extends MongoRepository<ApiConfig, String> {

    ApiConfig findByService(String service);

}