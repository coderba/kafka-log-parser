package com.idea.consumer.config;

import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.exceptions.TransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;

public class RetryingCassandraClusterFactoryBean extends CassandraClusterFactoryBean {

    private static final Logger LOG =
            LoggerFactory.getLogger(RetryingCassandraClusterFactoryBean.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
    }

    private void connect() throws Exception {
        try {
            super.afterPropertiesSet();
        } catch (TransportException | IllegalArgumentException | NoHostAvailableException e) {
            LOG.warn(e.getMessage());
            LOG.warn("Retrying connection in 2 seconds");
            sleep();
            connect();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
    }
}
