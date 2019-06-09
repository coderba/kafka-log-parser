package com.idea.producer.schedule;

import com.idea.producer.util.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

@Component
public class Sender {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);
    private static HashMap<City, Long> cityLongHashMap = new HashMap<>();

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

    private String parseMessage(String splitValue) {
        if (splitValue.contains(City.ISTANBUL.toString())) {
            if (cityLongHashMap.containsKey(City.ISTANBUL)) {
                cityLongHashMap.put(City.ISTANBUL, cityLongHashMap.get(City.ISTANBUL) + 1);
            } else {
                cityLongHashMap.put(City.ISTANBUL, 1l);
            }
            return City.ISTANBUL.toString();
        } else if (splitValue.contains(City.BEIJING.toString())) {
            if (cityLongHashMap.containsKey(City.BEIJING)) {
                cityLongHashMap.put(City.BEIJING, cityLongHashMap.get(City.BEIJING) + 1);
            } else {
                cityLongHashMap.put(City.BEIJING, 1l);
            }
            return City.BEIJING.toString();
        } else if (splitValue.contains(City.MOSKOW.toString())) {
            if (cityLongHashMap.containsKey(City.MOSKOW)) {
                cityLongHashMap.put(City.MOSKOW, cityLongHashMap.get(City.MOSKOW) + 1);
            } else {
                cityLongHashMap.put(City.MOSKOW, 1l);
            }
            return City.MOSKOW.toString();
        } else if (splitValue.contains(City.TOKYO.toString())) {
            if (cityLongHashMap.containsKey(City.TOKYO)) {
                cityLongHashMap.put(City.TOKYO, cityLongHashMap.get(City.TOKYO) + 1);
            } else {
                cityLongHashMap.put(City.TOKYO, 1l);
            }
            return City.TOKYO.toString();
        } else {
            if (cityLongHashMap.containsKey(City.LONDON)) {
                cityLongHashMap.put(City.LONDON, cityLongHashMap.get(City.LONDON) + 1);
            } else {
                cityLongHashMap.put(City.LONDON, 1l);
            }
            return City.LONDON.toString();
        }
    }

}
