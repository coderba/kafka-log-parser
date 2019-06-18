FROM openjdk:10-jre-slim
ARG JAR_FILE
ADD target/${JAR_FILE} producer.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/producer.jar"]