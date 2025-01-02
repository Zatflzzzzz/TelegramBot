package com.serviceSpring.FirstService_telegramBot;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication()
@Data
public class TelegramBotApplication {
    public static void main(String[] args) {
		SpringApplication.run(TelegramBotApplication.class, args);
	}
}