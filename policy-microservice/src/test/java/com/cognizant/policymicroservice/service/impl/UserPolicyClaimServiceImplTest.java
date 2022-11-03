package com.cognizant.policymicroservice.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.policymicroservice.exception.InvalidClaimException;
import com.cognizant.policymicroservice.exception.InvalidPolicyException;
import com.cognizant.policymicroservice.feign.WalletClient;
import com.cognizant.policymicroservice.model.PolicyMaster;
import com.cognizant.policymicroservice.model.Transaction;
import com.cognizant.policymicroservice.model.UserPolicyClaim;
import com.cognizant.policymicroservice.repository.PolicyMasterRepository;
import com.cognizant.policymicroservice.repository.UserPolicyClaimRepository;

@SpringBootTest
class UserPolicyClaimServiceImplTest {

	@MockBean
	UserPolicyClaimRepository userPolicyClaimRepo;

	@MockBean
	WalletClient walletClient;

	@MockBean
	PolicyMasterRepository policyMasterRepository;

	@Autowired
	UserPolicyClaimServiceImpl service;

	@Test
	void testViewClaim() {
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "medical", 100, 1000, "user");
		UserPolicyClaim claimObj = new UserPolicyClaim(1L, new Date(), 5000, "success", new Date(), "user", pObj);
		when(userPolicyClaimRepo.findAll()).thenReturn(Stream
				.of(new UserPolicyClaim(1L, new Date(), 5000, "success", new Date(), "user", pObj),
						new UserPolicyClaim(2L, new Date(), 5000, "success", new Date(), "user", pObj))
				.collect(Collectors.toList()));
		assertThat(service.viewClaim("user").size()).isEqualTo(0);
	}
	@Test
	void testUpdateClaim_ValidData() throws Exception{
		Long id = 1L;
		PolicyMaster policyObj = new PolicyMaster(id, "POL_1", "medical", 100, 1000, "user");
		UserPolicyClaim userClaim = new UserPolicyClaim(id, new Date(), 500, "success", new Date(), "user", policyObj);
		String token = "Bearer token";
		when(policyMasterRepository.findById(id))
				.thenReturn(Optional.ofNullable(policyObj));
		when(userPolicyClaimRepo.findById(id))
				.thenReturn(Optional.of(userClaim));
		when(walletClient.claimDeposit(token, new Transaction())).thenReturn((double) 100);
		when(userPolicyClaimRepo.save(userClaim)).thenReturn(userClaim);
		when(policyMasterRepository.save(policyObj)).thenReturn(policyObj);
		assertThat(service.updateClaim(id, userClaim, token)).isNotNull();
		verify(userPolicyClaimRepo, Mockito.times(1)).save(userClaim);
	}

	@Test
	void testUpdateClaim_InvalidPolicyException() throws Exception{
		Long id = 1L;
		PolicyMaster policyObj = new PolicyMaster(id, "POL_1", "medical", 100, 1000, "user");
		UserPolicyClaim userClaim = new UserPolicyClaim(id, new Date(), 500, "success", new Date(), "user", policyObj);
		String token = "Bearer token";
		when(policyMasterRepository.findById(id))
				.thenReturn(Optional.ofNullable(policyObj));
		when(userPolicyClaimRepo.findById(id))
				.thenReturn(Optional.empty());
		when(walletClient.claimDeposit(token, new Transaction())).thenReturn((double) 100);
		when(userPolicyClaimRepo.save(userClaim)).thenReturn(userClaim);
		when(policyMasterRepository.save(policyObj)).thenReturn(policyObj);
		NoSuchElementException thrown = assertThrows(NoSuchElementException.class, ()->service.updateClaim(id, userClaim, token));
		assertTrue(thrown.getMessage().contains("requested claim does not match with policy!"));
	}
	
	@Test
	void testUpdateClaim_ClaimNotFoundException() throws Exception{
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "medical", 100, 1000, "user");
		UserPolicyClaim claimObj = new UserPolicyClaim(1L, new Date(), 500, "success", new Date(), "user", pObj);
		String token = "Bearer token";
		when(policyMasterRepository.findById(1L))
				.thenReturn(Optional.of(new PolicyMaster(1L, "POL_1", "medical", 100, 1000, "user")));
		when(userPolicyClaimRepo.findById(1L))
				.thenReturn(Optional.of(new UserPolicyClaim(1L, new Date(), 5000, "success", new Date(), "user", pObj)));
		when(walletClient.claimDeposit(token, new Transaction())).thenReturn((double) 100);
		when(userPolicyClaimRepo.save(claimObj)).thenReturn(claimObj);
		InvalidClaimException thrown = assertThrows(InvalidClaimException.class,
				() -> service.updateClaim(1L, claimObj, token));
		assertTrue(thrown.getMessage().contains("claim not found!"));
	}

	@Test
	void testUpdateClaim_InvalidData_Exception() throws Exception{
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "medical", 100, 1000, "user");
		UserPolicyClaim claimObj = new UserPolicyClaim(1L, new Date(), 5000, "success", new Date(), "user", pObj);
		String token = "Bearer token";
		when(policyMasterRepository.findById(1L))
				.thenReturn(Optional.of(new PolicyMaster(1L, "POL_1", "medical", 100, 1000, "user")));
		when(userPolicyClaimRepo.findById(1L))
				.thenReturn(Optional.of(new UserPolicyClaim(1L, new Date(), 5000, "success", new Date(), "user", pObj)));
		when(walletClient.claimDeposit(token, new Transaction())).thenReturn((double) 100);
		when(userPolicyClaimRepo.save(claimObj)).thenReturn(claimObj);
		InvalidClaimException thrown = assertThrows(InvalidClaimException.class,
				() -> service.updateClaim(1L, claimObj, token));
		assertTrue(thrown.getMessage().contains("claim amount should not be greater then policy coverage"));
	}

}
