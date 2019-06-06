package com.idea.consumer.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table(value = "report")
public class Report {

    @PrimaryKeyColumn(name = "ID", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID ID;

    @Column()
    private String key;

    @Column()
    private String value;

    public Report(UUID ID, String key, String value) {
        this.ID = ID;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
