package com.idea.consumer.service;

import com.datastax.driver.core.utils.UUIDs;
import com.idea.consumer.Util.City;
import com.idea.consumer.entity.Report;
import com.idea.consumer.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class Receiver {

    @Autowired
    ReportRepository reportRepository;

    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private static HashMap<City, Long> cityLongHashMap = new HashMap<>();

    @KafkaListener(topics = "${app.topic}")
    public void listen(@Payload String message) {
        logger.info("received message='{}'", message);
        parseMessage(message);

        reportRepository.save(new Report(UUIDs.timeBased(), "Istanbul", "1"));
    }

    private HashMap<City, Long> parseMessage(String splitValue) {
        if (splitValue.contains(City.ISTANBUL.toString())) {
            if (cityLongHashMap.containsKey(City.ISTANBUL)) {
                cityLongHashMap.put(City.ISTANBUL, cityLongHashMap.get(City.ISTANBUL) + 1);
            } else {
                cityLongHashMap.put(City.ISTANBUL, 1l);
            }
        } else if (splitValue.contains(City.BEIJING.toString())) {
            if (cityLongHashMap.containsKey(City.BEIJING)) {
                cityLongHashMap.put(City.BEIJING, cityLongHashMap.get(City.BEIJING) + 1);
            } else {
                cityLongHashMap.put(City.BEIJING, 1l);
            }
        } else if (splitValue.contains(City.MOSKOW.toString())) {
            if (cityLongHashMap.containsKey(City.MOSKOW)) {
                cityLongHashMap.put(City.MOSKOW, cityLongHashMap.get(City.MOSKOW) + 1);
            } else {
                cityLongHashMap.put(City.MOSKOW, 1l);
            }
        } else if (splitValue.contains(City.TOKYO.toString())) {
            if (cityLongHashMap.containsKey(City.TOKYO)) {
                cityLongHashMap.put(City.TOKYO, cityLongHashMap.get(City.TOKYO) + 1);
            } else {
                cityLongHashMap.put(City.TOKYO, 1l);
            }
        } else {
            if (cityLongHashMap.containsKey(City.LONDON)) {
                cityLongHashMap.put(City.LONDON, cityLongHashMap.get(City.LONDON) + 1);
            } else {
                cityLongHashMap.put(City.LONDON, 1l);
            }
        }
        return cityLongHashMap;
    }

}