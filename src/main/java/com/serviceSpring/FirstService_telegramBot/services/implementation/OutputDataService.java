package com.serviceSpring.FirstService_telegramBot.services.implementation;

import com.serviceSpring.FirstService_telegramBot.models.*;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class OutputDataService {
    private int COUNT_OF_PRODUCTS = 0;
    private int COUNT_SHOWED_PRODUCTS = 0;

    private final DataReceiverService dataReceiverService;
    private final JsonParserService jsonParserService;
    private CatalogWrapper catalogWrapper;

    public OutputDataService(DataReceiverService dataReceiverService, JsonParserService jsonParserService) throws IOException {
        this.dataReceiverService = dataReceiverService;
        this.jsonParserService = jsonParserService;
        this.catalogWrapper = this.jsonParserService.getCatalogWrapper();

        if (this.catalogWrapper == null) {
            throw new IllegalStateException("Failed to load catalog wrapper from JsonOnlinerService");
        }
    }

    public List<String> getCatalogs() throws IOException {
        if (catalogWrapper == null || catalogWrapper.getCatalogs() == null) {
            throw new IllegalStateException("Catalog wrapper or its catalogs are null");
        }

        List<String> listOfCatalogsName = new ArrayList<>();
        catalogWrapper.getCatalogs().forEach(catalog -> listOfCatalogsName.add(catalog.getName()));

        return listOfCatalogsName;
    }

    public List<String> getCategoriesOfCatalog(String nameOfCatalog) throws IOException {
        Catalog currentCatalog = jsonParserService.getCatalogByName(nameOfCatalog);

        if (currentCatalog == null || currentCatalog.getCategories() == null) {
            throw new IllegalStateException("Failed to load categories for catalog: " + nameOfCatalog);
        }

        List<String> listOfCategoriesNames = new ArrayList<>();
        currentCatalog.getCategories().forEach(category -> listOfCategoriesNames.add(category.getName()));

        return listOfCategoriesNames;
    }

    public List<String> getSubDirectoriesOfCategory(String nameOfCatalog, String nameOfCategory) throws IOException {
        Category currentCategory = jsonParserService.getCategoryByName(nameOfCatalog, nameOfCategory);

        if (currentCategory == null || currentCategory.getSubCategories() == null) {
            throw new IllegalStateException("Failed to load subcategories for category: " + nameOfCategory);
        }

        List<String> listOfSubDirectoriesNames = new ArrayList<>();
        currentCategory.getSubCategories().forEach(subCategory -> listOfSubDirectoriesNames.add(subCategory.getName()));

        return listOfSubDirectoriesNames;
    }

    public List<GeneralProduct> getFiveSubCategoryProducts(String nameOfCatalog, String nameOfCategory, String nameOfSubCategory) throws IOException {
        SubCategory subCategory = jsonParserService.getSubCategoryByName(nameOfCatalog, nameOfCategory, nameOfSubCategory);

        List<GeneralProduct> listOfSubCategoryElements = dataReceiverService.getProductsInfoFromFivePages(subCategory.getRequest_link_query());
        List<GeneralProduct> resultListOfSubCategoryElements = new ArrayList<>();

        if (listOfSubCategoryElements == null || listOfSubCategoryElements.isEmpty()) {
            throw new IllegalStateException("Failed to load products for subcategory: " + nameOfSubCategory);
        }

        COUNT_OF_PRODUCTS = listOfSubCategoryElements.size();

        int start = COUNT_SHOWED_PRODUCTS;
        int end = Math.min(start + 5, COUNT_OF_PRODUCTS);

        for (int i = start; i < end; i++) {
            resultListOfSubCategoryElements.add(listOfSubCategoryElements.get(i));
        }

        COUNT_SHOWED_PRODUCTS = end;

        return resultListOfSubCategoryElements;
    }

    public List<GeneralProduct> getSixSubCategoryProductsFromCurrentPage(String nameOfCatalog, String nameOfCategory, String nameOfSubCategory, int page) throws IOException {
        SubCategory subCategory = jsonParserService.getSubCategoryByName(nameOfCatalog, nameOfCategory, nameOfSubCategory);

        List<GeneralProduct> listOfSubCategoryElements = dataReceiverService.getSixProductsInfoFromCurrentPage(subCategory.getRequest_link_query(), page);
        List<GeneralProduct> resultListOfSubCategoryElements = new ArrayList<>();

        if (listOfSubCategoryElements == null || listOfSubCategoryElements.isEmpty()) {
            throw new IllegalStateException("Failed to load products for subcategory: " + nameOfSubCategory);
        }

        COUNT_OF_PRODUCTS = listOfSubCategoryElements.size();

        int start = COUNT_SHOWED_PRODUCTS;
        int end = Math.min(start + 6, COUNT_OF_PRODUCTS);

        for (int i = start; i < end; i++) {
            resultListOfSubCategoryElements.add(listOfSubCategoryElements.get(i));
        }

        COUNT_SHOWED_PRODUCTS = end;

        return resultListOfSubCategoryElements;
    }

    public List<GeneralProduct> getTenPrimeSubCategoryElements(String nameOfCatalog, String categoryName, String subCategoryName) throws IOException {
        SubCategory subCategory = jsonParserService.getSubCategoryByName(nameOfCatalog, categoryName, subCategoryName);

        List<GeneralProduct> listOfProducts = dataReceiverService.getProductsInfoFromFivePages(subCategory.getRequest_link_query());

        if (listOfProducts == null || listOfProducts.isEmpty()) {
            throw new IllegalStateException("Failed to load products for subcategory: " + subCategoryName);
        }

        // Удаляем дубликаты перед сортировкой
        List<GeneralProduct> uniqueProducts = listOfProducts.stream()
                .distinct()
                .collect(Collectors.toList());

        List<GeneralProduct> sortedListOfProducts = quickSort(uniqueProducts, 0, uniqueProducts.size() - 1);
        List<GeneralProduct> resultArrayOfPrimeProducts = new ArrayList<>();

        for (int i = 0; i < Math.min(10, sortedListOfProducts.size()); i++) {
            resultArrayOfPrimeProducts.add(sortedListOfProducts.get(i));
        }

        return resultArrayOfPrimeProducts;
    }

    private List<GeneralProduct> quickSort(List<GeneralProduct>listOfProducts, int low, int high){
        if(low < high){
            int index_pivot = partition(listOfProducts, low, high);

            quickSort(listOfProducts, low, index_pivot - 1);
            quickSort(listOfProducts, index_pivot + 1, high);
        }

        return listOfProducts;
    }

    private static int partition(List<GeneralProduct> listOfProducts, int low, int high) {
        GeneralProduct pivot = listOfProducts.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (calculateScore(listOfProducts.get(j)) > calculateScore(pivot)) {
                i++;
                GeneralProduct temp = listOfProducts.get(i);
                listOfProducts.set(i, listOfProducts.get(j));
                listOfProducts.set(j, temp);
            }
        }

        GeneralProduct temp = listOfProducts.get(i + 1);
        listOfProducts.set(i + 1, listOfProducts.get(high));
        listOfProducts.set(high, temp);

        return i + 1;
    }

    private static double calculateScore(GeneralProduct product) {
        double ratingWeight = 0.7;
        double countWeight = 0.3;

        double ratingScore = product.getReviewRating() * ratingWeight;
        double countScore = product.getReviewsCount() * countWeight;

        return ratingScore + countScore + Math.random()*(50) * 1e-6;
    }
}