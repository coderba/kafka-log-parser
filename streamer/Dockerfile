FROM openjdk:10-jre-slim
ARG JAR_FILE
ADD target/${JAR_FILE} streamer.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/streamer.jar"]