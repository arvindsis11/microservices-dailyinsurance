package com.cognizant.authorizationmicroservice.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtRequestTest {
	
	private JwtRequest jwtReqAllArg = new JwtRequest("admin", "password");
	
	@Test
	void testUserNameGetter()
	{
		assertThat(jwtReqAllArg.getUsername().equals("admin")).isTrue();
	}

}
