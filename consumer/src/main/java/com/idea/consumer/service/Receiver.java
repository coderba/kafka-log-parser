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

    @Autowired
    private CassandraOperations cassandraTemplate;


    @Autowired
    SimpMessagingTemplate template;

    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private static HashMap<City, Long> cityLongHashMap = new HashMap<>();

    @KafkaListener(topics = "${app.topic}")
    public void listen(@Payload String message) {
        logger.info("received message='{}'", message);
        template.convertAndSend("/topic/temperature", message);
    }


    //    @Scheduled(fixedDelay = 5 * 60 * 1000)
    @Scheduled(fixedDelay = 2 * 1000)
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

}