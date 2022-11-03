package com.cognizant.walletmicroservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cognizant.walletmicroservice.feign.AuthorizeClient;
import com.cognizant.walletmicroservice.model.Transaction;
import com.cognizant.walletmicroservice.model.Wallet;
import com.cognizant.walletmicroservice.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class WalletControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthorizeClient authclient;

	@MockBean
	TransactionRepository transRepo;

	@Test
	void testAddWalletValidUser() throws Exception {
		Wallet wObj = new Wallet(1L, "pay", 100, "user", new Date());
		String jsonObj = this.mapToJson(wObj);
		when(authclient.authorize("test")).thenReturn(true);
		MvcResult mvresult = mockMvc.perform(post("/wallet/add-wallet-balance").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvresult.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	void testAddWallet_NotValidUser() throws Exception {
		Wallet wObj = new Wallet(1L, "pay", 100, "user", new Date());
		String jsonObj = this.mapToJson(wObj);
		when(authclient.authorize("test")).thenReturn(false);
		MvcResult mvresult = mockMvc.perform(post("/wallet/add-wallet-balance").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvresult.getResponse().getStatus()).isEqualTo(500);
	}

	@Test
	void testViewWalletes_ValidUser() throws Exception {
		Wallet wObj = new Wallet(1L, "pay", 100, "user", new Date());
		String jsonObj = this.mapToJson(wObj);
		when(authclient.authorize("test")).thenReturn(true);
		MvcResult mvres = mockMvc.perform(get("/wallet/view-all-wallets").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvres.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	void testViewWalletes_NotValidUser() throws Exception {
		Wallet wObj = new Wallet(1L, "pay", 100, "user", new Date());
		String jsonObj = this.mapToJson(wObj);
		when(authclient.authorize("test")).thenReturn(false);
		MvcResult mvres = mockMvc.perform(get("/wallet/view-all-wallets").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvres.getResponse().getStatus()).isEqualTo(500);
	}

	@Test
	void testGetTotalBalance() throws Exception {
		Optional<Transaction> tObj = Optional.of(new Transaction("user",1000));
		String jsonObj = this.mapToJson(tObj);
		when(authclient.authorize("test")).thenReturn(true);
		when(transRepo.findByUsername("user")).thenReturn(tObj);
		MvcResult mvres = mockMvc.perform(get("/wallet/total-balance").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvres.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	void testPolicyWithdraw1() throws Exception {
		Optional<Transaction> tObj = Optional.of(new Transaction("user",1000));
		String jsonObj = this.mapToJson(tObj);
		when(authclient.authorize("test")).thenReturn(true);
		when(transRepo.findByUsername("user")).thenReturn(tObj);
		MvcResult mvres = mockMvc.perform(post("/wallet/policy-balance").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvres.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	void testClaimDeposit() throws Exception {
		Optional<Transaction> tObj = Optional.of(new Transaction("user",1000));
		String jsonObj = this.mapToJson(tObj);
		when(authclient.authorize("test")).thenReturn(true);
		when(transRepo.findByUsername("user")).thenReturn(tObj);
		MvcResult mvres = mockMvc.perform(post("/wallet/claim-balance").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvres.getResponse().getStatus()).isEqualTo(200);
	}
	@Test
	void testPolicyWithdraw1_denied() throws Exception {
		Optional<Transaction> tObj = Optional.of(new Transaction("user",1000));
		String jsonObj = this.mapToJson(tObj);
		when(authclient.authorize("test")).thenReturn(false);
		when(transRepo.findByUsername("user")).thenReturn(tObj);
		MvcResult mvres = mockMvc.perform(post("/wallet/policy-balance").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvres.getResponse().getStatus()).isEqualTo(500);
	}

	@Test
	void testClaimDeposit_denied() throws Exception {
		Optional<Transaction> tObj = Optional.of(new Transaction("user",-1000));
		String jsonObj = this.mapToJson(tObj);
		when(authclient.authorize("test")).thenReturn(false);
		when(transRepo.findByUsername("user")).thenReturn(tObj);
		MvcResult mvres = mockMvc.perform(post("/wallet/claim-balance").header("Authorization", "test")
				.contentType("application/json").content(jsonObj)).andReturn();
		assertThat(mvres.getResponse().getStatus()).isEqualTo(500);
	}

	String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
