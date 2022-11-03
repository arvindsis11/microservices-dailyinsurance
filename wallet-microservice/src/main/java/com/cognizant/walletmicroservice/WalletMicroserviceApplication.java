package com.cognizant.walletmicroservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WalletMicroserviceApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(WalletMicroserviceApplication.class);

	public static void main(String[] args) {
		logger.debug("WalletMicroserviceApplication");
		SpringApplication.run(WalletMicroserviceApplication.class, args);
	}

}
