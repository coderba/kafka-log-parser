package com.idea.producer.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;

@Component
public class Sender {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.topic}")
    private String topic;

    @Value("${app.log.file.location}")
    private String logFileLocation;


    public void send(String message) {
        logger.info("sending message='{}' to topic='{}'", message, topic);
        kafkaTemplate.send(topic, message);
    }

    @PostConstruct
    public void read() {

        if (logFileLocation == null || logFileLocation.isEmpty()) {
            logger.error("[read] please enter app.log.file.location on application.properties.");
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(
                        logFileLocation));
                String line;

                while (true) {
                    line = reader.readLine();
                    if (line == null) {
                        //do nothing...
                    } else {
                        send(line);
                    }
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                logger.error("[read]", e);
            }
        }

    }
}
