package com.hostchecker.controller.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostchecker.service.LogRecordService;
import com.hostchecker.entity.LogRecord;

@RestController
@RequestMapping("/api/view")
public class LogController {
	
	@Autowired
    private LogRecordService logRecordService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/logs")
    public ResponseEntity<List<LogRecord>> getAuditLogs() {
        List<LogRecord> logList = logRecordService.getAllAuditLogs();
        return ResponseEntity.ok(logList);
    }

}
