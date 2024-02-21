package com.hostchecker.service;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostchecker.config.user.User;
import com.hostchecker.config.user.UserRepository;

import ch.qos.logback.core.util.Duration;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void checkAndLockUser(Integer id) {
		
		User user = userRepository.findById(id).orElse(null);
		
		if( user != null && !user.isLocked()) {
			Date lastActivityTimeDate = user.getLastActivityTime();
			Date currentTimeDate = new Date();
			
			long durationInMillis = currentTimeDate.getTime() - lastActivityTimeDate.getTime();
	        long durationInMinutes = TimeUnit.MILLISECONDS.toMinutes(durationInMillis);

	        if (durationInMinutes > 3) {
	            user.setLocked(true);
	            userRepository.save(user);
	        }
					
		}			
	}

}
