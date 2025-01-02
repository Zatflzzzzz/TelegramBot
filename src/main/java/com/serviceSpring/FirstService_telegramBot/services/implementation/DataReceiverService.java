package com.serviceSpring.FirstService_telegramBot.services.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.serviceSpring.FirstService_telegramBot.models.GeneralProduct;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@Service
public class DataReceiverService {
    public static int COUNT_OF_PAGES = 5;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public DataReceiverService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
        this.objectMapper = objectMapper;
    }

    public List<GeneralProduct> getProductsInfoFromFivePages(String query) {
        try {
            String jsonResponse = fetchDataFromController(query);

            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode productsNode = root.path("page");
            ArrayNode mainNode = (ArrayNode) root.path("products");

            COUNT_OF_PAGES = Math.min(Integer.parseInt(productsNode.path("last").asText()), 5);

            for (int i = 2; i <= COUNT_OF_PAGES; i++) {
                jsonResponse = fetchDataFromController(query + "?page=" + i);
                JsonNode newPageRoot = objectMapper.readTree(jsonResponse);
                ArrayNode newPageProducts = (ArrayNode) newPageRoot.path("products");

                for (JsonNode product : newPageProducts) {
                    mainNode.add(product);
                }
            }

            return getGeneralProducts(mainNode);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<GeneralProduct> getSixProductsInfoFromCurrentPage(String query, int page) {
        try{
            String jsonResponse = fetchDataFromController(query+"?page="+page);

            JsonNode root = objectMapper.readTree(jsonResponse);
            ArrayNode mainNode = (ArrayNode) root.path("products");

            return getGeneralProducts(mainNode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<GeneralProduct> getGeneralProducts(ArrayNode mainNode) {
        return StreamSupport.stream(mainNode.spliterator(), false)
                .map(productNode -> {
                    GeneralProduct product = new GeneralProduct();
                    product.setName(productNode.path("name").asText());
                    product.setManufacturer(productNode.path("manufacturer").path("name").asText());
                    product.setDescription(productNode.path("micro_description").asText());
                    product.setPrice(productNode.path("prices").path("price_min").path("amount").asText());
                    product.setImageUrl(productNode.path("images").path("header").asText());
                    product.setProductUrl(productNode.path("html_url").asText());
                    product.setFullDescription(productNode.path("description").asText());
                    product.setReviewsCount(productNode.path("reviews").path("count").asInt());
                    product.setReviewRating(productNode.path("reviews").path("rating").asInt());
                    product.setMinPrice(productNode.path("prices").path("price_min").path("amount").asText());
                    product.setMaxPrice(productNode.path("prices").path("price_max").path("amount").asText());

                    return product;
                })
                .collect(Collectors.toList());
    }

    private String fetchDataFromController(String query){
        Mono<String> response = webClient
                .get()
                .uri("/api/Project/getItems/" + query)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.just("{\"error\": \"Ошибка при запросе данных\", \"message\": \"" + e.getMessage() + "\"}"));

        return response.block();
    }
}