package com.hostchecker.controller.host;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostchecker.dto.HostDto;
import com.hostchecker.dto.HostRegRequest;
import com.hostchecker.dto.HostUpdateRequest;
import com.hostchecker.entity.Host;
import com.hostchecker.service.HostService;

@RestController
@RequestMapping("api/host")
public class HostController {

	@Autowired
	private HostService hostService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/register")
	public ResponseEntity<Host> registerHost(@RequestBody HostRegRequest request){
		System.out.println("User roles: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		return ResponseEntity.ok(hostService.registerhost(request));
		
	}
	
	@GetMapping("/viewhosts")
    public ResponseEntity<List<HostDto>> getAllHosts() {
        List<HostDto> hosts = hostService.getAllHosts();
        return ResponseEntity.ok(hosts);
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<Host> updateHost(@PathVariable Integer id, @RequestBody HostUpdateRequest updatedHost) {
        Host host = hostService.updateHost(id, updatedHost);
        if (host != null) {
            return ResponseEntity.ok(host);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	 @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteHost(@PathVariable Integer id) {
	        if (hostService.deleteHost(id)) {
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	
	
}
