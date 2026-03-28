package org.katu.springrevision.cache;

import org.katu.springrevision.config.ApiConfig;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApiConfigCache {
    private final Map<String, ApiConfig> cache = new ConcurrentHashMap<>();

    public void put(String service, ApiConfig config) {
        cache.put(service, config);
    }

    public ApiConfig get(String service) {
        return cache.get(service);
    }
}
