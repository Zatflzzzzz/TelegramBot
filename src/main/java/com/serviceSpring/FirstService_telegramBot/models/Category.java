package com.serviceSpring.FirstService_telegramBot.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Category {
    private String name;
    @JsonProperty("sub-categories")
    private List<SubCategory> subCategories;
}
