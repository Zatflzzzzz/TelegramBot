package com.serviceSpring.FirstService_telegramBot;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@Data
public class FirstServiceTelegramBotApplication {
    public static void main(String[] args) {
		SpringApplication.run(FirstServiceTelegramBotApplication.class, args);
	}
}