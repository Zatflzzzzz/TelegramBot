# Telegram Bot Helper для выбора устройства

Это мой первый проект, написанный на **Java** с использованием **Spring Framework**. Проект представляет собой Telegram-бота, который помогает пользователям выбирать устройства на основе их предпочтений или требований.

---

## Используемые технологии

- **Java**: Основной язык программирования.
- **Spring Boot**: Фреймворк для создания приложения.
- **Telegram Bot API**: Для интеграции с Telegram и создания бота.
- **Maven**: Для управления зависимостями и сборки проекта.
- **Lombok**: Для сокращения шаблонного кода.
- **Docker** (опционально): Для контейнеризации приложения и его развертывания.

---

## Возможности

- **Помощь в выборе устройства**: Бот взаимодействует с пользователями, чтобы помочь им выбрать устройство (например, смартфон, ноутбук и т.д.) на основе указанных критериев.
- **Удобный интерфейс**: Простой и понятный интерфейс через Telegram.

---

## Необходимые условия

Перед запуском бота убедитесь, что у вас установлено следующее:

1. **Java Development Kit (JDK)**: Убедитесь, что у вас установлен JDK 17 или выше.
2. **Spring Framework**: Проект использует Spring Boot для простоты и масштабируемости.
3. **Токен Telegram-бота**: Вам понадобится токен для вашего бота, который можно получить, создав бота в Telegram.

---

## Начало работы

### 1. Клонирование репозитория

    bash
    git clone <repository-url>
    cd <project-directory>

## 2. Создание бота в Telegram

Если у вас еще нет бота, выполните следующие шаги для его создания:

1. Откройте Telegram и найдите **BotFather**.
2. Начните чат с **BotFather**, нажав кнопку **"Start"**.
3. Используйте команду `/newbot` для создания нового бота.
4. Следуйте инструкциям, чтобы указать:
   - Имя вашего бота (например, `DeviceHelperBot`).
   - Уникальное имя пользователя, оканчивающееся на `bot` (например, `DeviceHelperBot`).
5. После создания **BotFather** отправит вам токен. Сохраните этот токен, так как он понадобится для настройки вашего бота.

---

## 3. Настройка приложения

Для запуска бота необходимо указать следующие поля в файле `application.properties` (или передать их как переменные окружения):

    properties
    bot.name=YourBotUsernameHere
    bot.token=YourTelegramBotTokenHere

## 5. Использование бота

После запуска бота:

1. Откройте Telegram и найдите вашего бота по его имени пользователя.
2. Начните диалог, нажав кнопку **"Start"**.
3. Следуйте инструкциям бота, чтобы получить рекомендации по устройствам.

---

## Запуск через Docker-Compose

Для удобства развертывания приложения можно использовать Docker и Docker-Compose. Ниже приведена инструкция по запуску проекта с помощью Docker-Compose.

### 1. Установите Docker и Docker-Compose

Убедитесь, что у вас установлены Docker и Docker-Compose. Если они не установлены, следуйте официальной документации для их установки.

### 2. Запуск приложения с помощью Docker-Compose

Выполните следующую команду для запуска приложения:

```
   docker-compose up --build
```

Приложение будет запущено в контейнере Docker, и бот начнет работать.

### Часть информации для этого проекта была взята с сайта Onliner.

### Дополнительная информация

    Поддержка: Если у вас возникли проблемы с запуском бота, пожалуйста, создайте issue в репозитории.

    Лицензия: Этот проект распространяется под лицензией MIT. Подробности можно найти в файле LICENSE.
