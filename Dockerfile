FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
ARG JAR_FILE=./target/**.jar
COPY ${JAR_FILE} /app/app.jar
EXPOSE 8085
CMD ["java","-jar","/app/app.jar"]
