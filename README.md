# Telegram Bot Helper for Choosing a Device

This is my first project written in **Java**, using the **Spring Framework**. The project implements a Telegram bot that helps users choose devices based on their preferences or requirements.

---

## Technologies Used

- **Java**: Core programming language.
- **Spring Boot**: Framework for building the application.
- **Telegram Bot API**: To integrate with Telegram and create a bot.
- **Maven**: For dependency management and project build.
- **Lombok**: To reduce boilerplate code.
- **SLF4J**: For logging functionality.
- **Docker** (optional): To containerize the application for deployment.

---

## Features

- **Device Selection Assistance**: The bot interacts with users to help them select a device (e.g., a smartphone, laptop, etc.) based on specified criteria.
- **User-Friendly**: Simple and easy-to-use interface via Telegram.

---

## Prerequisites

Before you can run the bot, ensure you have the following:

1. **Java Development Kit (JDK)**: Make sure you have JDK 17 or higher installed.
2. **Spring Framework**: The project uses Spring Boot for simplicity and scalability.
3. **Telegram Bot Token**: You'll need a token for your bot, which you can obtain by creating a bot in Telegram.

---

## Getting Started

### 1. Clone the Repository

bash
git clone <repository-url>
cd <project-directory>

### 2. Set Up Your Bot in Telegram

#### If you don’t have a bot yet, follow these steps to create one:

    Open Telegram and search for BotFather.
    Start a chat with the BotFather by clicking the "Start" button.
    Use the /newbot command to create a new bot.
    Follow the instructions to provide:
        A name for your bot (e.g., DeviceHelperBot).
        A unique username ending with bot (e.g., DeviceHelperBot).
    Once created, BotFather will send you a token. Save this token, as you'll need it to configure your bot.

### 3. Configure the Application

To run the bot, you need to provide the following fields in the application.properties file (or pass them as environment variables):
properties

bot.name=YourBotUsernameHere
bot.token=YourTelegramBotTokenHere

Replace YourBotUsernameHere and YourTelegramBotTokenHere with the actual values for your bot.

### 4. Build and Run the Project

Use the following steps to build and run the application:

    Build the project:
    bash

./mvnw clean package

Run the application:
bash

    java -jar target/<your-jar-file>.jar

How to Use the Bot

Once the bot is up and running:

    Open Telegram and find your bot using its username.
    Start a conversation by clicking the "Start" button.
    Follow the bot's instructions to get device recommendations.

Contributing

Contributions are welcome! If you'd like to improve this bot or add features, feel free to fork the repository and submit a pull request.

### Contact
[https://t.me/](https://t.me/zatflz)
