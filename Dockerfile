FROM maven:3.8.6-eclipse-temurin-17

WORKDIR .
COPY . .
#RUN mvn clean install
EXPOSE 8080

CMD mvn spring-boot:run