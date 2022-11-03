package com.cognizant.processclaimmicroservice.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.processclaimmicroservice.feign.AuthorizeClient;
import com.cognizant.processclaimmicroservice.feign.PolicyClient;
import com.cognizant.processclaimmicroservice.model.UserPolicyClaim;
import com.cognizant.processclaimmicroservice.repository.UserPolicyClaimRepository;

@SpringBootTest
@AutoConfigureMockMvc
class UserPolicyClaimServiceImplTest {


	@MockBean
	UserPolicyClaimRepository userPolicyClaimRepo;

	@MockBean
	PolicyClient policyClient;
	
	@MockBean
	AuthorizeClient auth;
	
	@Autowired
	UserPolicyClaimServiceImpl service;
	
	
	@Test
	void testUpdateClaim() {
		UserPolicyClaim claim = new UserPolicyClaim(1L, new Date(), 500, "approved", new Date(), "user");
		
		when(userPolicyClaimRepo.save(claim)).thenReturn(claim);
		when(policyClient.updateUserPolicyClaim(1L, claim, "token")).thenReturn(claim);
		//when(auth.authorize("token")).thenReturn(true);
		service.updateClaim(1L, claim, "token");
		assertThat(service.updateClaim(1L, claim, "token")).isEqualTo(claim);
		
	}

}
