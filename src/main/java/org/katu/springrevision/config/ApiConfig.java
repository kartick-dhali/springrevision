package org.katu.springrevision.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "api_config")
@Getter
@Setter
public class ApiConfig {
    @Id
    private String id;
    private String service;
    private String url;
    private String apiKey;

}
