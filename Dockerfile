FROM maven:3.8.8-eclipse-temurin-11 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY --from=build /app/target/*.jar daily-report-telegram-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/daily-report-telegram-0.0.1-SNAPSHOT.jar"]
