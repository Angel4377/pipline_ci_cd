FROM eclipse-temurin:17-jdk

LABEL maintainer="Angela"

WORKDIR /app

COPY target/price-service.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]