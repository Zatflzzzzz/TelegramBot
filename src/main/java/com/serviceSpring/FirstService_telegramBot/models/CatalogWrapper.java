package com.serviceSpring.FirstService_telegramBot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CatalogWrapper {
    @JsonProperty("catalog")  // Add this annotation
    private List<Catalog> catalogs;
}
