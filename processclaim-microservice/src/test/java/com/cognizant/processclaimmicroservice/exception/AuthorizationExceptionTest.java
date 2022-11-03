package com.cognizant.processclaimmicroservice.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.processclaimmicroservice.feign.PolicyClient;
import com.cognizant.processclaimmicroservice.service.impl.UserPolicyClaimServiceImpl;

class AuthorizationExceptionTest {

	@Autowired
	UserPolicyClaimServiceImpl service;
	
	@MockBean
	PolicyClient pclient;
	
	@Test
	void testAuthorizationException() {
		
		
	}

}
