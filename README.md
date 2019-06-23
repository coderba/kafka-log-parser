# How to run?
* docker-compose up

# Summary
spring boot with kafka

We have 3 service
* Streamer, which is creating log file as like they are sending from different city servers. (please change log location on log4j.xml)
* Producer, which is reading log file and sending kafka topic. (please change log file location on application.properties.)
* Consumer, reads kafka topic and calculates incoming logs from different cities and writes them to Cassandra DB and routing these information to "home" web page via "websocket".

* PS: "home" page runs on localhost:8082/home url.
* PS 2: Needs java 9



