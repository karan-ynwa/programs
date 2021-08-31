package com.ericsson.eus.aps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AutopayServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AutopayServiceApplication.class, args); 
	}
	
}
