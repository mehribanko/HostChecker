package com.hostchecker.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hostchecker.config.user.Role;
import com.hostchecker.config.user.User;
import com.hostchecker.config.user.UserRepository;
import com.hostchecker.dto.AuthRequest;
import com.hostchecker.dto.AuthResponse;
import com.hostchecker.dto.RegisterRequest;

@Service
public class AuthenticationService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService  jwtService;
	
	@Autowired
	private AuthenticationManager authManager;

	public AuthResponse register(RegisterRequest request) {
		
		User user = new User();
		Role role = Role.fromString(request.getRole());
		user.setFistname(request.getFirstname());
		user.setLastname(request.getLastname());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(role);
		
		userRepository.save(user);
		
		var jwt = jwtService.generateToken(user);
		
		AuthResponse authResponse = new AuthResponse();
		
		authResponse.setToken(jwt);
		authResponse.setUsername(request.getEmail());
		return authResponse;
	}

	public AuthResponse authenticate(AuthRequest request) {
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(), request.getPassword())
				);
		
		
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow();
		
		user.setLastActivityTime(new Date());
		
		userRepository.save(user);
		
		var jwt = jwtService.generateToken(user);
		
		AuthResponse authResponse = new AuthResponse();
		
		authResponse.setToken(jwt);
		authResponse.setUsername(user.getEmail());
		return authResponse;
	}
}
