# kafka-log-parser
spring boot with kafka

# Install Cassandra DB via Docker
* docker pull cassandra;

# Run Cassandra DB
* docker run -p 9042:9042 --rm --name cassandra -d cassandra:3.0

# Install Kafka via Docker
* docker pull spotify/kafka

# Run Kafka
* docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=`docker-machine ip \`docker-machine active\`` --env ADVERTISED_PORT=9092 spotify/kafka
* export KAFKA=`docker-machine ip \`docker-machine active\``:9092 kafka-console-producer.sh --broker-list $KAFKA --topic test
* export ZOOKEEPER=`docker-machine ip \`docker-machine active\``:2181 kafka-console-consumer.sh --zookeeper $ZOOKEEPER --topic test

# OR Install Kafka via "Brew"
* brew install kafka

# Run Kafka which is installed locally with Brew
* zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties
* kafka-server-start /usr/local/etc/kafka/server.properties

# Create kafka topic
* kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

We have 3 service
* Streamer, which is creating log file as like they are sending from different city servers. (please change log location on log4j.xml)
* Producer, which is reading log file and sending kafka topic. (please change log file location on application.properties.)
* Consumer, reads kafka topic and calculates incoming logs from different cities and writes them to Cassandra DB and routing these information to "home" web page via "websocket".

* PS: "home" page runs on localhost:8082/home url.
