package org.katu.springrevision.constant;

import lombok.Getter;

@Getter
public enum ApiService {
    OPEN_WEATHER("openweathermap"),
    PLACE_HOLDER("placeholder");

    private final String value;

    ApiService(String value) {
        this.value = value;
    }
}
