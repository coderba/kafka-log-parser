FROM openjdk:10-jre-slim
ARG JAR_FILE
ADD target/${JAR_FILE} app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]