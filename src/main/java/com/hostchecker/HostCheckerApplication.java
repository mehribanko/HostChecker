package com.hostchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class HostCheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HostCheckerApplication.class, args);
	}

}
