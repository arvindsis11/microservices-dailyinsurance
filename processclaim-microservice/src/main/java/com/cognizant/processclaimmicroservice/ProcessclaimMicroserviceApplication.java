package com.cognizant.processclaimmicroservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProcessclaimMicroserviceApplication {

	private static final Logger logger = LoggerFactory.getLogger(ProcessclaimMicroserviceApplication.class);
	
	public static void main(String[] args) {
		
		logger.debug("ProcessclaimMicroserviceApplication");
		
		SpringApplication.run(ProcessclaimMicroserviceApplication.class, args);
	}

}
