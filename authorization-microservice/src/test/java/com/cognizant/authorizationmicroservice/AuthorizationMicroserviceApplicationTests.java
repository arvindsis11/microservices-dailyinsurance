package com.cognizant.authorizationmicroservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class AuthorizationMicroserviceApplicationTests {

	@Autowired
	MockMvc mvc;
	
	@Test
	void contextLoads() {
	}
	@Test
	  public void applicationStarts() {
		AuthorizationMicroserviceApplication.main(new String[] {});
	  }

}
