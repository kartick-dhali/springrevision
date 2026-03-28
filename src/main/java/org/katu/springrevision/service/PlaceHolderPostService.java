package org.katu.springrevision.service;

import org.katu.springrevision.DTO.PlaceHolderPostRequest;
import org.katu.springrevision.DTO.PlaceHolderPostResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PlaceHolderPostService {
    private final WebClient webClient;

    public PlaceHolderPostService(WebClient webClient) {
        this.webClient = webClient;
    }
    public PlaceHolderPostResponse createPost(PlaceHolderPostRequest request) {

        return webClient.post()
                .uri("https://jsonplaceholder.typicode.com/posts")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PlaceHolderPostResponse.class)
                .block();
    }
}
