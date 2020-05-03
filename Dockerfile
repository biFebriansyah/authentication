FROM openjdk:8-jdk-alpine

COPY target/authentication-0.0.1-SNAPSHOT.jar /app/authentication.jar

CMD ["java", "-jar", "/app/authentication.jar"]