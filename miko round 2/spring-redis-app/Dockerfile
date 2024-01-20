FROM openjdk:21-jdk-slim

WORKDIR /app

COPY . /app

RUN ./mvnw clean install

EXPOSE 8080

CMD ["java", "-jar", "target/spring-redis-app-0.0.1-SNAPSHOT.jar"]
