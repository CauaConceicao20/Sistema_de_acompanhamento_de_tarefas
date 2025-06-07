# Stage 1: Build com Maven 3.9.9 e JDK 17
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime com Tomcat 11.0.7 e JDK 17
FROM tomcat:11.0.7-jdk17-temurin

RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/sistema-de-acompanhamento-de-tarefas-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

COPY src/main/resources/database.db /usr/local/tomcat/data/database.db
COPY src/main/resources/script.sql /usr/local/tomcat/data/script.sql

EXPOSE 8080

CMD ["catalina.sh", "run"]