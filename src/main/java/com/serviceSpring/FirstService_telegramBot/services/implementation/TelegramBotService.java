package com.serviceSpring.FirstService_telegramBot.services.implementation;

import com.serviceSpring.FirstService_telegramBot.config.TelegramBotConfig;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Service
@Data
public class TelegramBotService extends TelegramLongPollingBot {
    private final TelegramBotConfig telegramBotConfig;
    private final OutputDataService outputDataService;
    private final TelegramModelsService telegramModelsService;
    private String text = "12121";
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery(), update);
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            init();
            Long chatId = update.getMessage().getChatId();

            try {
                SendMessage message = new SendMessage();
                message.setChatId(chatId);

                switch (update.getMessage().getText()){
                    case "/start":{
                        message.setText("Уважаемый " + update.getMessage().getFrom().getUserName() + ", добро пожаловать в наш сервис!\n\n" +
                                "Выберите команды из списка, чтобы продолжить работу:\n\n" +
                                "/show_catalog - отобразить все каталоги доступные в данный период времени!" );

                        execute_message(message);
                        break;
                    }
                    case "/show_catalog":{
                        execute_message(telegramModelsService.sendButtons_catalog(chatId));
                        break;
                    }
                    default:{
                        message.setText("Простите, неизвестная команда :(");
                        execute_message(message);

                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery, Update update) {
        String data = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();

        try {
            if (data.startsWith("catalog_")) {
                deleteMessage(chatId, messageId);
                String nameOfCatalog = data.replace("catalog_", "");

                execute_message(telegramModelsService.sendButtons_category(chatId, nameOfCatalog));
            } else if (data.startsWith("cat_")) {
                deleteMessage(chatId, messageId);
                String[] parts = data.split("_", 3);

                String nameOfCatalog = parts[1];
                String nameOfCategory = parts[2];

                execute_message(telegramModelsService.sendButtons_subCategory(chatId, nameOfCatalog, nameOfCategory));
            } else if (data.startsWith("_")) {
                deleteMessage(chatId, messageId);
                String[] parts = data.split("_", 4);

                String nameOfCatalog = parts[1];
                String nameOfCategory = parts[2];
                String nameOfSubCategory = parts[3];

                execute_message(telegramModelsService.sendText_additionalInformationAndFunctions(chatId,
                        nameOfCatalog, nameOfCategory, nameOfSubCategory));
            }
            else if(data.startsWith("r")){
                String[] parts = data.split("_", 4);

                String nameOfCatalog = parts[1];
                String nameOfCategory = parts[2];
                String nameOfSubCategory = parts[3];

                execute_message(telegramModelsService.sendText_InformationOfSubCategory(chatId,nameOfCategory,nameOfCategory,nameOfSubCategory));

                List<SendPhoto> productPhotoMessages = telegramModelsService.sendProductPhotoMessagesOfFiveProducts(chatId,
                        nameOfCatalog, nameOfCategory, nameOfSubCategory);

                try {
                    Thread.sleep(1500); // Задержка в 1 секунду
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (SendPhoto photoMessage : productPhotoMessages) {
                    execute_message(photoMessage);
                }

                execute_message(telegramModelsService.sendText_additionalInformationAndFunctions(chatId,
                        nameOfCatalog, nameOfCategory, nameOfSubCategory));
            }
            else if(data.startsWith("t")){
                String[] parts = data.split("_", 4);

                String nameOfCatalog = parts[1];
                String nameOfCategory = parts[2];
                String nameOfSubCategory = parts[3];

                execute_message(telegramModelsService.sendText_InformationOfSubCategory(chatId,
                        nameOfCatalog, nameOfCategory, nameOfSubCategory));

                List<SendPhoto> productPhotoMessages = telegramModelsService.sendSevenPrimeSubCategoryElements(chatId,
                        nameOfCatalog, nameOfCategory, nameOfSubCategory);

                try {
                    Thread.sleep(1500); // Задержка в 1 секунду
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (SendPhoto photoMessage : productPhotoMessages) {
                    execute_message(photoMessage);
                }

                execute_message(telegramModelsService.sendText_additionalInformationAndFunctions(chatId,
                        nameOfCatalog, nameOfCategory, nameOfSubCategory));
            }
            else if(data.startsWith("getp")){
                String[] parts = data.split("_", 4);

                String nameOfCatalog = parts[1];
                String nameOfCategory = parts[2];
                String nameOfSubCategory = parts[3];

                execute_message(telegramModelsService.sendButtons_allAvailablePages(chatId,
                        nameOfCatalog, nameOfCategory, nameOfSubCategory));
            }
            else if(data.startsWith("p")){
                String[] parts = data.split("_", 5);
                
                int page = Integer.parseInt(parts[1]);
                String nameOfCatalog = parts[2];
                String nameOfCategory = parts[3];
                String nameOfSubCategory = parts[4];
                
                List<SendPhoto> productPhotoMessages = telegramModelsService.sendProductPhotoMessagesFromCurrentPage(chatId,
                        nameOfCatalog, nameOfCategory, nameOfSubCategory, page);

                try {
                    Thread.sleep(1500); // Задержка в 1 секунду
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (SendPhoto photoMessage : productPhotoMessages) {
                    execute_message(photoMessage);
                }

                execute_message(telegramModelsService.sendText_additionalInformationAndFunctions(chatId,
                        nameOfCatalog, nameOfCategory, nameOfSubCategory));
            }
            else if(data.startsWith("showAgainCatalog")){
                deleteMessage(chatId, messageId);
                execute_message(telegramModelsService.sendButtons_catalog(chatId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteMessage(Long chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);

        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void execute_message(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void execute_message(SendPhoto message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() {
        try {
            List<BotCommand> botCommands = telegramModelsService.createCommands();
            execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfig.getBotToken();
    }
}