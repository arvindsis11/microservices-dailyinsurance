package com.cognizant.walletmicroservice.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TransactionTest {

	Transaction obj = new Transaction();
	
	@Test
	void testGetId() {
		obj.setUsername("username");
		assertThat(obj.getUsername()).isEqualTo(1L);
	}

	@Test
	void testGetAmount() {
		obj.setAmount(100);
		assertThat(obj.getAmount()).isEqualTo(100);
	}
	@Test
	void testTransactionLongDouble() {
		obj = new Transaction("user",100);
		assertEquals(obj, new Transaction("user",100));
	}

	@Test
	void testTransaction() {
		obj = new Transaction();
		assertEquals(obj, new Transaction());
	}

}
