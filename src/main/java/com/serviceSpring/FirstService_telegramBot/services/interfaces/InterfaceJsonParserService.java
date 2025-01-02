package com.serviceSpring.FirstService_telegramBot.services.interfaces;

import com.serviceSpring.FirstService_telegramBot.models.Catalog;
import com.serviceSpring.FirstService_telegramBot.models.CatalogWrapper;
import com.serviceSpring.FirstService_telegramBot.models.Category;
import com.serviceSpring.FirstService_telegramBot.models.SubCategory;

import java.io.IOException;

public interface InterfaceJsonParserService {
    CatalogWrapper getCatalogWrapper() throws IOException;

    Catalog getCatalogByName(String nameOfCatalog) throws IOException;

    Category getCategoryByName(String nameOfCatalog, String nameOfDirectory) throws IOException;

    SubCategory getSubCategoryByName(String nameOfCatalog,String nameOfDirectory, String nameOfSubCategory) throws IOException;
}
