package com.serviceSpring.FirstService_telegramBot.services.implementation;

import com.serviceSpring.FirstService_telegramBot.models.GeneralProduct;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class TelegramModelsService {
    private final OutputDataService outputDataService;
    private final JsonParserService jsonParserService;
    private final DataReceiverService dataReceiverService;

    List<BotCommand> createCommands(){
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Начать взаимодействие с ботом"));
        listOfCommands.add(new BotCommand("/show_catalog", "Показать каталог"));
        listOfCommands.add(new BotCommand("/help", "Информация о командах"));

        return listOfCommands;
    }

    public SendMessage sendButtons_catalog(long chatId) throws IOException {
        SendMessage message = new SendMessage();
        List<String> listOfCatalogsNames = outputDataService.getCatalogs();

        message.setChatId(chatId);
        message.setText("Изучите наш выбор каталогов, которые могут вам предоставить информацию про продуктами и услугами с onliner." +
                "Просто выберите интересующий вас каталог и начните поиск!");

        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (String catalogName : listOfCatalogsNames) {
            keyboard.add(List.of(createButton("📁 " + catalogName, "catalog_" + catalogName)));
        }

        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }

    public SendMessage sendButtons_category(long chatId, String nameOfCatalog) throws IOException {
        SendMessage message = new SendMessage();
        List<String> listOfCategoryNames = outputDataService.getCategoriesOfCatalog(nameOfCatalog);

        message.setChatId(chatId);
        message.setText("📂 Вы выбрали каталог: `" + nameOfCatalog + "`\n\n" +
                "Выберите одну из категорий ниже, чтобы продолжить.");

        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (String categoryName : listOfCategoryNames) {
            keyboard.add(List.of(createButton("📑 " + categoryName, "cat_" + nameOfCatalog + "_" + categoryName)));
        }

        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }

    public SendMessage sendButtons_allAvailablePages(long chatId, String nameOfCatalog, String nameOfCategory, String nameOfSubCategory) throws IOException {
        SendMessage message = new SendMessage();

        message.setChatId(chatId);
        message.setText("""
                Вы можете выбрать с какой страницы вам отобразить предметы по выбранной подкатегории. 
                
                Сразу после выбора страницы вам отобразиться информация про первые пять элементов с onliner!""");

        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for(int i = 1; i <= DataReceiverService.COUNT_OF_PAGES; i++){
            keyboard.add(List.of(createButton("Страница номер " + i, "p" +"_" + i + "_" + nameOfCatalog + "_" + nameOfCategory + "_" + nameOfSubCategory)));
        }

        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }

    private InlineKeyboardButton createButton(String name, String data) {
        var inline = new InlineKeyboardButton();
        inline.setText(name);
        inline.setCallbackData(data);

        return inline;
    }

    public List<SendPhoto> sendProductPhotoMessagesOfFiveProducts(long chatId, String nameOfCatalog, String nameOfCategory, String nameOfSubCategory) throws IOException {
        List<GeneralProduct> listOfProducts = outputDataService.getFiveSubCategoryProducts(nameOfCatalog, nameOfCategory, nameOfSubCategory);

        return getProductPhotoMessages(chatId, listOfProducts);
    }

    public List<SendPhoto> sendProductPhotoMessagesFromCurrentPage(long chatId, String nameOfCatalog, String nameOfCategory, String nameOfSubCategory, int page) throws IOException {
        List<GeneralProduct> listOfProducts = outputDataService.getSixSubCategoryProductsFromCurrentPage(nameOfCatalog, nameOfCategory, nameOfSubCategory, page);

        return getProductPhotoMessages(chatId, listOfProducts);
    }

    public List<SendPhoto> sendSevenPrimeSubCategoryElements(long chatId, String nameOfCatalog, String nameOfCategory, String nameOfSubCategory) throws IOException {
        List<GeneralProduct> listOfPrimeProducts = outputDataService.getTenPrimeSubCategoryElements(nameOfCatalog,nameOfCategory, nameOfSubCategory);

        return getProductPhotoMessages(chatId, listOfPrimeProducts);
    }

    private List<SendPhoto> getProductPhotoMessages(long chatId,List<GeneralProduct> listOfProducts){
        List<SendPhoto> productPhotoMessages = new ArrayList<>();

        for (GeneralProduct product : listOfProducts) {
            SendPhoto productPhotoMessage = getInformationOfProduct(chatId, product);

            productPhotoMessages.add(productPhotoMessage);
        }

        return productPhotoMessages;
    }

    public SendPhoto getInformationOfProduct(long chatId, GeneralProduct product) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);

        InputFile photo = new InputFile(product.getImageUrl());
        sendPhoto.setPhoto(photo);

        String caption = String.format(
                "Название: %s\nПроизводитель: %s\nПолное описание: %s\nМинимальная цена: %s\nМаксимальная цена: %s\nКоличество отзывов: %d\nРейтинг: %.1f/10\nСсылка на товар: %s",
                product.getName(),
                product.getManufacturer(),
                product.getFullDescription(),
                product.getMinPrice(),
                product.getMaxPrice(),
                product.getReviewsCount(),
                (double) product.getReviewRating() * 2 / 10,
                product.getProductUrl()
        );

        sendPhoto.setCaption(caption);

        return sendPhoto;
    }

    public SendMessage sendText_InformationOfSubCategory(long chatId, String nameOfCatalog, String nameOfCategory, String nameOfSubCategory) throws IOException {
        SendMessage message = new SendMessage();

        message.setChatId(chatId);
        message.setText("Вы выбрали:\n📁 Каталог: `" + nameOfCatalog + "`\n" +
                "📋 Категория: `" + nameOfCategory + "`\n" +
                "🔹 Подкатегория: `" + nameOfSubCategory + "`\n\n" +
                "Выберите предметы из подкатегории по основным параметрам и смело заказывайте товар на онлайнере!.");

        return message;
    }

    public SendMessage sendButtons_subCategory(long chatId, String nameOfCatalog, String nameOfCategory) throws IOException {
        SendMessage message = new SendMessage();
        List<String> listOfSubCategoryNames = outputDataService.getSubDirectoriesOfCategory(nameOfCatalog, nameOfCategory);

        message.setChatId(chatId);
        message.setText("Вы выбрали:\n📁 Каталог: `" + nameOfCatalog + "`\n📋 Категория: `" + nameOfCategory + "`\n\n" +
                "Выберите подкатегорию из списка.");

        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (String subCategoryName : listOfSubCategoryNames) {
            keyboard.add(List.of(createButton("📂 " + subCategoryName, "_" + nameOfCatalog + "_" + nameOfCategory + "_" + subCategoryName)));
        }

        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }

    public SendMessage sendText_additionalInformationAndFunctions(long chatId, String nameOfCatalog, String nameOfCategory, String nameOfSubCategory) throws IOException {
        SendMessage message = new SendMessage();

        message.setChatId(chatId);
        message.setText("Вы выбрали: \n📁 Каталог: `" + nameOfCatalog + "`\n" +
                "📋 Категория: `" + nameOfCategory + "`\n" +
                "🔹 Подкатегория: `" + nameOfSubCategory + "`\n\n" +
                "\n\nВыберите какое действие вы хотите совершить:");

        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(List.of(createButton("Отобразить 5 предметов из текущей подкатегории!", "r" + "_" + nameOfCatalog + "_" + nameOfCategory + "_" + nameOfSubCategory)));
        keyboard.add(List.of(createButton("Отобразить 10 лучших предметов из текущей подкатегории!", "t" + "_" + nameOfCatalog + "_" + nameOfCategory + "_" + nameOfSubCategory)));
        keyboard.add(List.of(createButton("Выбрать страницу для отображения товара", "getp" + "_" + nameOfCatalog + "_" + nameOfCategory + "_" + nameOfSubCategory)));
        keyboard.add(List.of(createButton("Вернуться к выбору категории!", "showAgainCatalog")));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }
}