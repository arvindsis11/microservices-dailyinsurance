package com.cognizant.policymicroservice.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.policymicroservice.exception.InvalidPolicyException;
import com.cognizant.policymicroservice.feign.WalletClient;
import com.cognizant.policymicroservice.model.PolicyMaster;
import com.cognizant.policymicroservice.repository.PolicyMasterRepository;
import com.cognizant.policymicroservice.repository.TransactionRepository;
import com.cognizant.policymicroservice.repository.UserPolicyClaimRepository;
import com.cognizant.policymicroservice.service.UserPolicyClaimService;

@SpringBootTest
@AutoConfigureMockMvc
class PolicyServiceImplTest {

	@MockBean
	WalletClient walletClient;

	@MockBean
	UserPolicyClaimService userPolicyClaimService;

	@MockBean
	PolicyMasterRepository policyMasterRepo;

	@MockBean
	UserPolicyClaimRepository userPolicyClaimRepository;

	@MockBean
	TransactionRepository transactionRepository;

	@Autowired
	PolicyServiceImpl service;

	@Test
	void testCreatePolicy_NotNull_ValidData() {
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "medical", 100, 0, "user");
		String token = "token";
		when(walletClient.getTotalBalance(token, "user")).thenReturn((double) 1000);
		when(policyMasterRepo.save(pObj)).thenReturn(pObj);
		service.createPolicy(pObj, token);
		assertThat(service.createPolicy(pObj, token)).isNotNull();
		verify(policyMasterRepo, Mockito.times(2)).save(pObj);

	}

	@Test
	void testCreatePolicy_InvalidData() {
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "medical", 1000, 0, "user");
		String token = "token";
		when(walletClient.getTotalBalance(token, "user")).thenReturn((double) 100);
		when(policyMasterRepo.save(pObj)).thenReturn(pObj);
		InvalidPolicyException thrown = assertThrows(InvalidPolicyException.class,
				() -> service.createPolicy(pObj, token));
		assertTrue(thrown.getMessage().contains("insufficient wallet balance!"));

	}

	@Test
	void testViewAllPolicy() {
		when(policyMasterRepo.findAll()).thenReturn(Stream.of(new PolicyMaster(1L, "POL_1", "medical", 100, 1000, "user"),new PolicyMaster(1L, "POL_1", "medical", 500, 5000, "user")).collect(Collectors.toList()));
		assertThat(service.viewPolicy("user").size()).isEqualTo(2);
		

	}

}
