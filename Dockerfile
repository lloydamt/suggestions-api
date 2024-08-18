FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
ARG JAR_FILE=suggestions-api.jar
RUN pwd
RUN ls -la
COPY ${JAR_FILE} /app/app.jar
EXPOSE 8085
CMD ["java","-jar","/app/app.jar"]
