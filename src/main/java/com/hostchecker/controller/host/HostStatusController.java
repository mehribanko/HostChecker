package com.hostchecker.controller.host;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostchecker.dto.HostStatus;
import com.hostchecker.service.HostService;

@RestController
@RequestMapping("/api/host")
public class HostStatusController {

	@Autowired
	private HostService hostService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{hostId}/status")
	public ResponseEntity<HostStatus> getHostStatus(@PathVariable Integer hostId) throws IOException{
		HostStatus hostStatus = hostService.getHostStatus(hostId);
		
		if(hostStatus != null) {
			return ResponseEntity.ok(hostStatus);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
