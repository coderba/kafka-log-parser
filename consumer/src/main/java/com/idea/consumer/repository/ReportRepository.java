package com.idea.consumer.repository;

import com.idea.consumer.entity.Report;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CassandraRepository<Report, Long> {

}
