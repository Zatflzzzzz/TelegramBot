package com.serviceSpring.FirstService_telegramBot.models;

import lombok.Data;

@Data
public class GeneralProduct {
    private String name;
    private String manufacturer;
    private String description;
    private String price;
    private String imageUrl;
    private String productUrl;
    private String fullDescription;
    private int reviewsCount;
    private int reviewRating;
    private String minPrice;
    private String maxPrice;
}
