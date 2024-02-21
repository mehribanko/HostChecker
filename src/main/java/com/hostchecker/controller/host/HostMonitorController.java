package com.hostchecker.controller.host;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hostchecker.dto.HostMonitor;
import com.hostchecker.service.HostService;

@RestController
@RequestMapping("/api/host")
public class HostMonitorController {
	
	@Autowired
	private HostService hostService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/monitor")
	public ResponseEntity<List<HostMonitor>> monitorHostStatus(@RequestParam(required = false) Integer hostId) throws IOException, InterruptedException, ExecutionException{
		
		List<HostMonitor> hostMonitors= hostService.monitorHostStatus(hostId);
		
		return ResponseEntity.ok(hostMonitors);
		
	}

}
