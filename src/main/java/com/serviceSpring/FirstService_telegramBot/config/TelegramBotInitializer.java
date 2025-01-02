package com.serviceSpring.FirstService_telegramBot.config;

import com.serviceSpring.FirstService_telegramBot.services.implementation.TelegramBotService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Data
public class TelegramBotInitializer {
    @Autowired
    TelegramBotService bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotApi = new TelegramBotsApi(DefaultBotSession.class);

        try{
            telegramBotApi.registerBot(bot);
        }catch (TelegramApiException e){
            throw new TelegramApiException(e.getMessage());
        }
    }
}
