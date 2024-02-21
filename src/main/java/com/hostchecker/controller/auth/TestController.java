package com.hostchecker.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
	
	
	@GetMapping
	public ResponseEntity<String> testEndpoint(Authentication authentication){
		 System.out.println("User roles: " + authentication.getAuthorities());
		return ResponseEntity.ok("authorized access");
	}

}
