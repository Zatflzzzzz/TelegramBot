package com.serviceSpring.FirstService_telegramBot.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serviceSpring.FirstService_telegramBot.models.Catalog;
import com.serviceSpring.FirstService_telegramBot.models.CatalogWrapper;
import com.serviceSpring.FirstService_telegramBot.models.Category;
import com.serviceSpring.FirstService_telegramBot.models.SubCategory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class JsonParserRepository {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final static String FILE_NAME = "src/main/resources/OnlinerData.json";

    private CatalogWrapper readCatalogFromFile() throws IOException {
        File file = new File(JsonParserRepository.FILE_NAME);

        if (!file.exists()) {
            System.out.println("File not found: " + JsonParserRepository.FILE_NAME);
            return null;
        }

        return objectMapper.readValue(file, CatalogWrapper.class);
    }

    public CatalogWrapper getCatalogWrapper() throws IOException {
        CatalogWrapper catalogWrapper = readCatalogFromFile();

        if (catalogWrapper == null || catalogWrapper.getCatalogs() == null || catalogWrapper.getCatalogs().isEmpty()) {
            System.out.println("Catalogs are null or empty");
            return null;
        }

        return catalogWrapper;
    }

    public Catalog getCatalogByName(String nameOfCatalog) throws IOException {
        CatalogWrapper catalogWrapper = getCatalogWrapper();
        List<Catalog> catalogs = catalogWrapper.getCatalogs();

        for(Catalog catalog : catalogs){
            if(catalog.getName().equalsIgnoreCase(nameOfCatalog)){
                return catalog;
            }
        }

        return null;
    }

    public Category getDirectoryByName(String nameOfCatalog, String nameOfDirectory) throws IOException {
        Catalog catalogs = getCatalogByName(nameOfCatalog);

        for(Category category : catalogs.getCategories()){
            if(category.getName().equalsIgnoreCase(nameOfDirectory)){
                return category;
            }
        }

        return null;
    }

    public SubCategory getSubCategory(String nameOfCatalog, String nameOfDirectory,String nameOfSubCategory) throws IOException {
        Category category = getDirectoryByName(nameOfCatalog, nameOfDirectory);

        for(SubCategory subCategory : category.getSubCategories()){
            if(subCategory.getName().equalsIgnoreCase(nameOfSubCategory)){
                return subCategory;
            }
        }
        System.out.println("Error with data");
        return null;
    }
}
