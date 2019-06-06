package com.idea.producer.schedule;

import com.idea.producer.util.FileTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Sender {

    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.topic}")
    private String topic;

    public void send(String message) {
        logger.info("sending message='{}' to topic='{}'", message, topic);
        kafkaTemplate.send(topic, "hello world");
    }

    @Scheduled(fixedDelay = 200)
    public void read() {
        List<String> list = new FileTool().read("/Users/cembasarbaskan/Desktop/test/log4j2-demo.log");
        logger.info("sending message='{}' to topic='{}'", list.get(list.size() - 1), topic);
        kafkaTemplate.send(topic, list.get(list.size() - 1));
    }
}
