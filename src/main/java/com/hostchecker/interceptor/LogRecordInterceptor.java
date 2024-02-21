package com.hostchecker.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.hostchecker.dto.AuthResponse;
import com.hostchecker.service.LogRecordService;

@Aspect
@Component
public class LogRecordInterceptor {
	
	@Autowired
    private LogRecordService logRecordService;

    @AfterReturning(pointcut = "execution(* com.hostchecker.service.AuthenticationService.authenticate(..))", returning = "result")
    public void logSuccessfulLogin(JoinPoint joinPoint, AuthResponse result) {
        String username = result.getUsername();
        logRecordService.logEvent("LOGIN", username, "Successful login");
    }

    @AfterReturning(pointcut = "execution(* com.hostchecker.controller.auth.AuthController.logout(..))", returning = "result")
    public void logLogout(JoinPoint joinPoint, Object result) {
        String username = getLoggedInUsername();
        logRecordService.logEvent("LOGOUT", username, "Logged out");
    }
    
    private String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "unknownUser";
    }
}
