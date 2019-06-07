package com.idea.consumer.entity;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@Table(value = "report")
public class Report {

    @PrimaryKeyColumn(name = "key", type = PARTITIONED)
    private String key;

    @Column()
    private long value;

    public Report(String key, long value) {
        this.key = key;
        this.value = value;
    }

    public Report() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
