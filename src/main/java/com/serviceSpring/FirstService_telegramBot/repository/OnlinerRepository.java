package com.serviceSpring.FirstService_telegramBot.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class OnlinerRepository {
    private final WebClient webClient;

    public OnlinerRepository(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.baseUrl("https://catalog.api.onliner.by").build();
    }

    public Mono<String> getItemsByQuery(String query) {
        return webClient
                .get()
                .uri("/search/" + query)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.just("Error fetching items"));
    }
}
