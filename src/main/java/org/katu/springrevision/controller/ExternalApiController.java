package org.katu.springrevision.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.katu.springrevision.DTO.PlaceHolderPostRequest;
import org.katu.springrevision.DTO.PlaceHolderPostResponse;
import org.katu.springrevision.service.PlaceHolderPostService;
import org.katu.springrevision.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/external")
@Tag(name = "External API", description = "Third part API Integration")
public class ExternalApiController {
    private final WeatherService weatherService;
    private final PlaceHolderPostService placeHolderPostService;
    public ExternalApiController(WeatherService weatherService, PlaceHolderPostService placeHolderPostService) {
        this.weatherService = weatherService;
        this.placeHolderPostService = placeHolderPostService;
    }

    @GetMapping("/hello/{city}")
    public ResponseEntity<Map<String, String>> hello(@PathVariable String city){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        assert authentication != null;
        String username =  authentication.getName();
        Double temp = weatherService.getTemperature(city);

        Map<String, String> response = new HashMap<>();

        if (temp == null) {
            response.put("message",
                    "Hello " + username + "! temperature is not available for city " + city);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.put("message",
                "Hello " + username + "! the current temperature is " + temp + "°C");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/placeholder")
    public ResponseEntity<PlaceHolderPostResponse> placeHolderPost(@RequestBody PlaceHolderPostRequest placeHolderPostRequest){
        PlaceHolderPostResponse response = placeHolderPostService.createPost(placeHolderPostRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
