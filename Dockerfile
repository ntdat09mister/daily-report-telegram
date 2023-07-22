FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/daily-report-telegram-0.0.1-SNAPSHOT.jar daily-report-telegram-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/daily-report-telegram-0.0.1-SNAPSHOT.jar"]