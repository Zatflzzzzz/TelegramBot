
FROM openjdk:22-ea-jdk-slim AS build
RUN apt-get update && apt-get install -y maven

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:22-ea-jdk-slim

COPY --from=build /app/target/*.jar app.jar
COPY src/main/resources/OnlinerData.json /app/OnlinerData.json

ENV onliner.data.file.path=/app/OnlinerData.json

ENTRYPOINT ["java", "-jar", "app.jar"]