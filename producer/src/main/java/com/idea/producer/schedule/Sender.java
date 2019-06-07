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

    public void send(String message) {
        logger.info("sending message='{}' to topic='{}'", message, topic);
        kafkaTemplate.send(topic, message);
    }

    @PostConstruct
    public void read() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "/Users/cembasarbaskan/Desktop/test/log4j2-demo.log"));
            String line;

            while (true) {
                line = reader.readLine();
                if (line == null) {
                    Thread.sleep(200);
                } else {
                    send(line);
                }
            }
        } catch (Exception e) {
            logger.error("[read]", e);
        }
    }
}
