package com.serviceSpring.FirstService_telegramBot.services.interfaces;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface InterfaceOnlinerService {
    Mono<Map<String, Object>> getItems(String query);
}
