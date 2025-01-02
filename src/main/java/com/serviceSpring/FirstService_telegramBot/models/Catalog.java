package com.serviceSpring.FirstService_telegramBot.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Catalog {
    private String name;
    @JsonProperty("categories")
    private List<Category> categories;
}
