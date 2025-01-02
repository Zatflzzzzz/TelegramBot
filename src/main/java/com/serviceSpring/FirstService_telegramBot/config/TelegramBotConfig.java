package com.serviceSpring.FirstService_telegramBot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
public class TelegramBotConfig {
    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String botToken;
}
