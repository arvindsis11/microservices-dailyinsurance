package com.cognizant.policymicroservice.exception;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.policymicroservice.feign.WalletClient;
import com.cognizant.policymicroservice.model.PolicyMaster;
import com.cognizant.policymicroservice.service.impl.PolicyServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
class InvalidPolicyExceptionTest {

	@Autowired
	PolicyServiceImpl service;
	@MockBean
	WalletClient walletClient;

	@Test
	void testInvalidPolicyException() {
		PolicyMaster pobj = new PolicyMaster(1L, "POL_1", "medical", 100, 1000, "user");
		when(walletClient.getTotalBalance("test", "user")).thenReturn((double) 10);
		InvalidPolicyException thrown = assertThrows(InvalidPolicyException.class,
				() -> service.createPolicy(pobj, "test"));
		assertTrue(thrown.getMessage().contains("insufficient wallet balance!"));

	}

}
