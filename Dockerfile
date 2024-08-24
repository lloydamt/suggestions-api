FROM maven:3.9.8-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY . .
RUN mvn install

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 5000
CMD ["java","-jar","/app/app.jar"]