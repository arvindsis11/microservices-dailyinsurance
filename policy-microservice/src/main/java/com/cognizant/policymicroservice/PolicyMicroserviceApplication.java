package com.cognizant.policymicroservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PolicyMicroserviceApplication {

	private static final Logger logger = LoggerFactory.getLogger(PolicyMicroserviceApplication.class);
	public static void main(String[] args) {
		logger.debug("PolicyMicroserviceApplication");
		SpringApplication.run(PolicyMicroserviceApplication.class, args);
	}

}
