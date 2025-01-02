package com.serviceSpring.FirstService_telegramBot.services.implementation;

import com.serviceSpring.FirstService_telegramBot.models.Catalog;
import com.serviceSpring.FirstService_telegramBot.models.CatalogWrapper;
import com.serviceSpring.FirstService_telegramBot.models.Category;
import com.serviceSpring.FirstService_telegramBot.models.SubCategory;
import com.serviceSpring.FirstService_telegramBot.repository.JsonParserRepository;
import com.serviceSpring.FirstService_telegramBot.services.interfaces.InterfaceJsonParserService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Data
@Service
public class JsonParserService implements InterfaceJsonParserService {
    private final JsonParserRepository jsonParserRepository;

    @Override
    public CatalogWrapper getCatalogWrapper() throws IOException {
        CatalogWrapper catalogWrapper = jsonParserRepository.getCatalogWrapper();

        if (catalogWrapper == null || catalogWrapper.getCatalogs() == null || catalogWrapper.getCatalogs().isEmpty()) {
            throw new IllegalStateException("Failed to load catalog data from JSON");
        }

        return catalogWrapper;
    }

    @Override
    public Catalog getCatalogByName(String nameOfCatalog) throws IOException {
        return jsonParserRepository.getCatalogByName(nameOfCatalog);
    }

    @Override
    public Category getCategoryByName(String nameOfCatalog, String nameOfDirectory) throws IOException {
        return jsonParserRepository.getDirectoryByName(nameOfCatalog, nameOfDirectory);
    }

    @Override
    public SubCategory getSubCategoryByName(String nameOfCatalog, String nameOfDirectory, String nameOfSubCategory) throws IOException {
        return jsonParserRepository.getSubCategory(nameOfCatalog,nameOfDirectory,nameOfSubCategory);
    }
}
