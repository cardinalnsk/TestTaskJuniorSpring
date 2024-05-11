FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /app
COPY src src
COPY pom.xml pom.xml
RUN mvn clean package -Dmaven.test.skip=true


FROM eclipse-temurin:21-jammy
RUN addgroup spring-boot-group && adduser --ingroup spring-boot-group spring-boot
USER spring-boot:spring-boot-group
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8089

ENTRYPOINT ["java", "-jar", "app.jar"]