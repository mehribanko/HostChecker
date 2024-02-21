package com.hostchecker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hostchecker.config.user.User;
import com.hostchecker.config.user.UserRepository;

@Service
public class UserLockService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	
	@Scheduled(fixedRate = 5*60*1000)
	public void lockInactiveUsers() {
		List<User> users = userRepository.findAll();
		for(User user : users) {
			userService.checkAndLockUser(user.getId());
		}
	}
}
