package com.hostchecker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostchecker.entity.LogRecord;
import com.hostchecker.repo.LogRecordRepository;

@Service
public class LogRecordService {
	
	@Autowired
	private LogRecordRepository logRecordRepository;
	
	public void logEvent(String eventType, String userIdentity, String eventResult) {
		
        LogRecord auditLog = new LogRecord();
        auditLog.setEventDateTime(LocalDateTime.now());
        auditLog.setEventType(eventType);
        auditLog.setUserIdentity(userIdentity);
   
        logRecordRepository.save(auditLog);
    }

	public List<LogRecord> getAllAuditLogs() {
		List<LogRecord> allList= logRecordRepository.findAll();
		return allList;
	}

}
