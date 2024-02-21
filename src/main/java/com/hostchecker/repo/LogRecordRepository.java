package com.hostchecker.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hostchecker.entity.LogRecord;

public interface LogRecordRepository extends JpaRepository<LogRecord, Integer>{

}
