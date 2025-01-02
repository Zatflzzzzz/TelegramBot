package com.serviceSpring.FirstService_telegramBot.services.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serviceSpring.FirstService_telegramBot.repository.OnlinerRepository;
import com.serviceSpring.FirstService_telegramBot.services.interfaces.InterfaceOnlinerService;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Data
public class OnlinerService implements InterfaceOnlinerService {
    private final OnlinerRepository repository;
    private final ObjectMapper objectMapper;

    public OnlinerService(OnlinerRepository repository, ObjectMapper objectMapper){
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Map<String, Object>> getItems(String query) {
        return repository.getItemsByQuery(query)
                .flatMap(response -> {
                    try {
                        Map<String, Object> json = objectMapper.readValue(response, Map.class);
                        return Mono.just(json);
                    } catch (Exception e) {
                        return Mono.error(new RuntimeException("Error parsing JSON"));
                    }
                });
    }
}
