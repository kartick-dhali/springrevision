package org.katu.springrevision.loader;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.katu.springrevision.cache.ApiConfigCache;
import org.katu.springrevision.config.ApiConfig;
import org.katu.springrevision.repository.ApiConfigRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ApiConfigLoader implements ApplicationRunner {

    private final ApiConfigRepository repository;
    private final ApiConfigCache cache;

    public ApiConfigLoader(ApiConfigRepository repository, ApiConfigCache cache) {
        this.repository = repository;
        this.cache = cache;
    }

    @Override
    public void run(@NonNull ApplicationArguments args) throws Exception {
        List<ApiConfig> configs = repository.findAll();
        for (ApiConfig config : configs) {
            cache.put(config.getService(), config);
        }
        log.info("ApiConfigCache loaded. {}", configs);
    }
}
