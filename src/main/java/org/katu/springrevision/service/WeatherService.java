package org.katu.springrevision.service;

import lombok.extern.slf4j.Slf4j;
import org.katu.springrevision.DTO.WeatherResponse;
import org.katu.springrevision.cache.ApiConfigCache;
import org.katu.springrevision.config.ApiConfig;
import org.katu.springrevision.constant.ApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
@Slf4j
public class WeatherService {
    private final WebClient webClient;
    private final ApiConfigCache cache;
    @Value("${weather.api.key}")
    private String apiKey;
    private final RedisTemplate<String, Object> redisTemplate;

    public WeatherService(WebClient webClient, ApiConfigCache cache, RedisTemplate<String, Object> redisTemplate){
        this.webClient = webClient;
        this.cache = cache;
        this.redisTemplate = redisTemplate;
    }
    public Double getTemperature(String city) {

        String key = "weather:" + city.toLowerCase();

        Object cacheTemp = redisTemplate.opsForValue().get(key);

        if (cacheTemp != null) {

            Double temp = Double.valueOf(cacheTemp.toString());

            log.info("Redis Cache : Temperature for {} is {} °C", city, temp);

            return temp;
        }
        try {

            log.info("Calling weather API for city: {}", city);
            ApiConfig config = cache.get(ApiService.OPEN_WEATHER.getValue());

            String url = config.getUrl()
                    .replace("<city>", city)
                    .replace("<key>", config.getApiKey());

            WeatherResponse response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(WeatherResponse.class)
                    .block();

            if (response != null && response.getMain() != null) {
                double temp = response.getMain().getTemp();
                redisTemplate.opsForValue()
                        .set(key, temp, Duration.ofMinutes(20));
                log.info("Temperature for {} is {} °C", city, temp);

                return temp;
            }

            log.warn("Weather API returned empty response for city: {}", city);
            return null;

        } catch (Exception e) {

            log.error("Error while calling weather API for city {}", city, e);
            throw new RuntimeException("Failed to fetch temperature");
        }
    }
}
