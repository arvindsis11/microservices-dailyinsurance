package com.cognizant.authorizationmicroservice.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class JwtTokenUtilTest {
	
	UserDetails userDetails;
	
	@Autowired
	private JwtTokenUtil util;
	
	
	@Test
	void testGenerateTokenWithRandomUserGeneratesNull()
	{
		UserDetails details = new org.springframework.security.core.userdetails.User("admin", "pass", new ArrayList<>());
		assertThat(util.generateToken(details)).isNotNull();
	}
	
	@Test
	void validateTokenTest()
	{
		userDetails = new User("admin", "admin", new ArrayList<>());
		String generateToken = util.generateToken(userDetails);
		Boolean validateToken = util.validateToken(generateToken, userDetails);
		assertThat(validateToken).isTrue();
	}

}
