package com.idea.producer.schedule;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StreamSchedular {
    private static Logger logger = LogManager.getLogger(StreamSchedular.class);

    @Scheduled(fixedDelay = 3 * 1000, initialDelay = 1000)
    private void logStreamer() {
        while (true) {
            if (new Date().getTime() % 2 == 0) {
                doLog(Level.INFO, "Istanbul", "hello-from-Istanbul");
            }

            if (new Date().getTime() % 3 == 0) {
                doLog(Level.WARN, "Tokyo", "hello-from-Tokyo");
            }

            if (new Date().getTime() % 5 == 0) {
                doLog(Level.FATAL, "Moskow", "hello-from-Moskow");
            }

            if (new Date().getTime() % 7 == 0) {
                doLog(Level.DEBUG, "Beijing", "hello-from-Beijing");
            }

            if (new Date().getTime() % 11 == 0) {
                doLog(Level.ERROR, "London", "hello-from-London");
            }
        }
    }

    private void doLog(Level logLevel, String city, String message){
        logger.log(logLevel, city + " " + message);
    }

}
