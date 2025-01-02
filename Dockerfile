FROM openjdk:17-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/FirstService_telegramBot-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
