# How to run?
* docker-compose up

# Summary
spring boot with kafka

We have 3 service
* Streamer, which is simulating as like logs are sending from different city servers. 
* Producer, which is reading log file and sending kafka topic.
* Consumer, reads kafka topic and calculates incoming logs from different cities and writes them to Cassandra DB and routing these information to "home" web page via "websocket".

* PS: "home" page runs on localhost:8082/home
* PS 2: Needs java 9



