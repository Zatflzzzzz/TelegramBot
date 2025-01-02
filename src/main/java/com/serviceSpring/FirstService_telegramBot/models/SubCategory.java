package com.serviceSpring.FirstService_telegramBot.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubCategory {
    private String name;

    @JsonProperty("request_link_query")
    private String request_link_query;
}
