package com.serviceSpring.FirstService_telegramBot.controllers;

import com.serviceSpring.FirstService_telegramBot.services.implementation.DataReceiverService;
import com.serviceSpring.FirstService_telegramBot.services.implementation.OnlinerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;


@RestController
@RequestMapping("/api/Project")
@Data
public class OnlinerController {
    @Autowired
    private OnlinerService onlinerService;
    private final DataReceiverService dataReceiverService;

    @GetMapping("/getItems/{query}")
    public Mono<ResponseEntity<Map<String, Object>>> getAllItems(@PathVariable String query) {
        return onlinerService.getItems(query)
                .map(json -> ResponseEntity.ok().body(json))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body(Map.of("error", "Error fetching items"))));
    }
}