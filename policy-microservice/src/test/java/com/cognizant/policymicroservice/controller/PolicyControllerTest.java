package com.cognizant.policymicroservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cognizant.policymicroservice.feign.AuthorizeClient;
import com.cognizant.policymicroservice.feign.WalletClient;
import com.cognizant.policymicroservice.model.PolicyMaster;
import com.cognizant.policymicroservice.model.UserPolicyClaim;
import com.cognizant.policymicroservice.repository.PolicyMasterRepository;
import com.cognizant.policymicroservice.repository.TransactionRepository;
import com.cognizant.policymicroservice.repository.UserPolicyClaimRepository;
import com.cognizant.policymicroservice.service.UserPolicyClaimService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class PolicyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthorizeClient authclient;

	@MockBean
	private WalletClient walletClient;

	@MockBean
	UserPolicyClaimService userPolicyClaimService;

	@MockBean
	PolicyMasterRepository policyMasterRepo;

	@MockBean
	UserPolicyClaimRepository userPolicyClaimRepository;

	@MockBean
	TransactionRepository transactionRepository;
	
	//fix-- use @Before for eliminating repetition!

	@Test
	void testMyPolicy_ValidToken() throws Exception {
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "Health", 100, 0, "user");
		String jsonObj = this.mapToJson(pObj);
		when(authclient.authorize("test")).thenReturn(true);
		when(walletClient.getTotalBalance("test", "user")).thenReturn((double) 500);
		MvcResult mvresult = mockMvc.perform(post("/policy/purchase-policy").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvresult.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	void testMyPolicy_InvalidToken() throws Exception {
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "Health", 100, 0, "user");
		String jsonObj = this.mapToJson(pObj);
		when(authclient.authorize("test")).thenReturn(false);
		when(walletClient.getTotalBalance("test", "user")).thenReturn((double) 500);
		MvcResult mvresult = mockMvc.perform(post("/policy/purchase-policy").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvresult.getResponse().getStatus()).isEqualTo(500);
	}

	@Test
	void testViewPolicy_ValidToken() throws Exception {
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "Health", 100, 0,"username");
		String jsonObj = this.mapToJson(pObj);
		when(authclient.authorize("test")).thenReturn(true);
		MvcResult mvres = mockMvc.perform(get("/policy/view-policy").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvres.getResponse().getStatus()).isEqualTo(404);
	}
	
	@Test
	void testViewPolicy_InValidToken() throws Exception {
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "Health", 100, 0,"username");
		String jsonObj = this.mapToJson(pObj);
		when(authclient.authorize("test")).thenReturn(false);
		MvcResult mvres = mockMvc.perform(get("/policy/view-policy").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvres.getResponse().getStatus()).isEqualTo(404);
	}


	

	@Test
	void testUpdateUserPolicyClaim_ValidToken() throws Exception {
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "Health", 100, 0, "user");
		Date date = new Date();
		UserPolicyClaim claimObj = new UserPolicyClaim(1L, date, 1000, "dummy", date, "user", pObj);
		String jsonObj = this.mapToJson(claimObj);
		when(authclient.authorize("test")).thenReturn(true);
		MvcResult mvres = mockMvc.perform(put("/policy/update-claim/1").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvres.getResponse().getStatus()).isEqualTo(200);
		
	}
	@Test
	void testUpdateUserPolicyClaim_InValidToken() throws Exception {
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "Health", 100, 0, "user");
		Date date = new Date();
		UserPolicyClaim claimObj = new UserPolicyClaim(1L, date, 1000, "dummy", date, "user", pObj);
		String jsonObj = this.mapToJson(claimObj);
		when(authclient.authorize("test")).thenReturn(false);
		MvcResult mvres = mockMvc.perform(put("/policy/update-claim/1").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvres.getResponse().getStatus()).isEqualTo(500);
		
	}

	@Test
	void testViewUserPolicyClaim_ValidData() throws Exception {
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "Health", 100, 0, "user");
		Date date = new Date();
		UserPolicyClaim claimObj = new UserPolicyClaim(1L, date, 1000, "dummy", date, "user", pObj);
		String jsonObj = this.mapToJson(claimObj);
		when(authclient.authorize("test")).thenReturn(true);
		MvcResult mvres = mockMvc.perform(get("/policy/view-claim").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvres.getResponse().getStatus()).isEqualTo(200);
	}
	
	@Test
	void testViewUserPolicyClaim_InValidData() throws Exception {
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "Health", 100, 0, "user");
		Date date = new Date();
		UserPolicyClaim claimObj = new UserPolicyClaim(1L, date, 1000, "dummy", date, "user", pObj);
		String jsonObj = this.mapToJson(claimObj);
		when(authclient.authorize("test")).thenReturn(false);
		MvcResult mvres = mockMvc.perform(get("/policy/view-claim").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvres.getResponse().getStatus()).isEqualTo(500);
	}


	String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
