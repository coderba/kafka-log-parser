package com.idea.consumer.repository;

import com.idea.consumer.entity.Report;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends CassandraRepository<Report, Long> {

    Optional<Report> findByKey(String key);
}
