package com.idea.producer.schedule;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.idea.producer.config.Constants.SLEEP_TIME;

@Component
public class StreamSchedular {
    private static Logger logger = LogManager.getLogger(StreamSchedular.class);

    @Scheduled(fixedDelay = 503)
    private void logStreamer() throws InterruptedException {
        while (true) {
            if (new Date().getTime() % 2 == 0) {
                logAndSleep(Level.INFO, "Istanbul", "hello-from-Istanbul");
            }

            if (new Date().getTime() % 3 == 0) {
                logAndSleep(Level.WARN, "Tokyo", "hello-from-Tokyo");
            }

            if (new Date().getTime() % 5 == 0) {
                logAndSleep(Level.FATAL, "Moskow", "hello-from-Moskow");
            }

            if (new Date().getTime() % 7 == 0) {
                logAndSleep(Level.DEBUG, "Beijing", "hello-from-Beijing");
            }

            if (new Date().getTime() % 11 == 0) {
                logAndSleep(Level.ERROR, "London", "hello-from-London");
            }
        }
    }

    private void logAndSleep(Level logLevel, String city, String message) throws InterruptedException {
        logger.log(logLevel, city + " " + message);
        new Thread().sleep(SLEEP_TIME);
    }

}
