FROM openjdk:10-jre-slim
ARG JAR_FILE
ADD target/${JAR_FILE} consumer.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/consumer.jar"]