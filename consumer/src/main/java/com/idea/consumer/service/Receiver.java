package com.idea.consumer.service;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.idea.consumer.Util.City;
import com.idea.consumer.entity.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Receiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private static HashMap<City, Long> cityLongHashMap = new HashMap<>();

    @Autowired
    private CassandraOperations cassandraTemplate;

    @Autowired
    private SimpMessagingTemplate template;


    @KafkaListener(topics = "${app.topic}")
    public void listen(@Payload String message) {
        logger.info("received message='{}'", message);
        parseMessage(message);
        template.convertAndSend("/topic/incomingLogs", message);
    }


    @Scheduled(fixedDelay = 5 * 1000, initialDelay = 1000)
    private void dbOperation() {
        for (Map.Entry<City, Long> entry : cityLongHashMap.entrySet()) {
            City key = entry.getKey();
            Long value = entry.getValue();
            insertOrUpdate(key.toString(), value);
        }

    }

    private void insertOrUpdate(String city, Long value) {
        Report reportTEMP = new Report();
        reportTEMP.setKey(city);
        Select select = QueryBuilder.select().from("report");
        select.where(QueryBuilder.eq("key", city));
        Report retrievedBook = cassandraTemplate.selectOne(select, Report.class);

        try {
            if (retrievedBook == null) {
                logger.info("[listen] data inserting...");
                reportTEMP.setValue(1);
                cassandraTemplate.insert(reportTEMP);
            } else {
                logger.info("[listen] data updating...");
                reportTEMP.setValue(value);
                cassandraTemplate.update(reportTEMP);
            }
        } catch (Exception e) {
            logger.error("[listen]", e);
        }
    }

    private String parseMessage(String message) {
        if (message.contains(City.ISTANBUL.toString())) {
            if (cityLongHashMap.containsKey(City.ISTANBUL)) {
                cityLongHashMap.put(City.ISTANBUL, cityLongHashMap.get(City.ISTANBUL) + 1);
            } else {
                cityLongHashMap.put(City.ISTANBUL, 1l);
            }
            return City.ISTANBUL.toString();
        } else if (message.contains(City.BEIJING.toString())) {
            if (cityLongHashMap.containsKey(City.BEIJING)) {
                cityLongHashMap.put(City.BEIJING, cityLongHashMap.get(City.BEIJING) + 1);
            } else {
                cityLongHashMap.put(City.BEIJING, 1l);
            }
            return City.BEIJING.toString();
        } else if (message.contains(City.MOSKOW.toString())) {
            if (cityLongHashMap.containsKey(City.MOSKOW)) {
                cityLongHashMap.put(City.MOSKOW, cityLongHashMap.get(City.MOSKOW) + 1);
            } else {
                cityLongHashMap.put(City.MOSKOW, 1l);
            }
            return City.MOSKOW.toString();
        } else if (message.contains(City.TOKYO.toString())) {
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