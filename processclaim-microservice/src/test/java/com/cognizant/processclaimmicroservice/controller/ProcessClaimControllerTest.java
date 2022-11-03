package com.cognizant.processclaimmicroservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;

import com.cognizant.processclaimmicroservice.feign.AuthorizeClient;
import com.cognizant.processclaimmicroservice.feign.PolicyClient;
import com.cognizant.processclaimmicroservice.model.UserPolicyClaim;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ProcessClaimControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	PolicyClient policyClient;

	@MockBean
	private AuthorizeClient authclient;

	@Test
	void testUpdateClaim_ValidToken() throws Exception {
		UserPolicyClaim claim = new UserPolicyClaim(1L, new Date(), 500, "approved", new Date(), "user");
		String jsonObj = this.mapToJson(claim);
		when(authclient.authorize("test")).thenReturn(true);
		when(policyClient.updateUserPolicyClaim(1L, claim, "test")).thenReturn(claim);
		MvcResult mvres = mockMvc.perform(put("/claim/update-claim/1").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvres.getResponse().getStatus()).isEqualTo(200);

	}

	@Test
	void testUpdateClaim_InValidToken() throws Exception {
		UserPolicyClaim claim = new UserPolicyClaim(1L, new Date(), 500, "approved", new Date(), "user");
		String jsonObj = this.mapToJson(claim);
		when(authclient.authorize("test")).thenReturn(false);
		when(policyClient.updateUserPolicyClaim(1L, claim, "test")).thenReturn(claim);
		NestedServletException thrown = assertThrows(NestedServletException.class,()->mockMvc.perform(put("/claim/update-claim/1").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn());
		assertTrue(thrown.getMessage().contains("Access Denied"));

	}

	String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
