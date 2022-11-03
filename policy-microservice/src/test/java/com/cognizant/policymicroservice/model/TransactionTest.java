package com.cognizant.policymicroservice.model;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TransactionTest {

	Transaction obj = new Transaction();
	
	@Test
	void testGetId() {
		obj.setUsername("user");
		assertThat(obj.getUsername()).isEqualTo("user");
	}

	@Test
	void testGetAmount() {
		obj.setAmount(100);
		assertThat(obj.getAmount()).isEqualTo(100);
	}
	@Test
	void testTransactionLongDouble() {
		obj = new Transaction(100,"user");
		assertEquals(obj, new Transaction(100,"user"));
	}

	@Test
	void testTransaction() {
		obj = new Transaction();
		assertEquals(obj, new Transaction());
	}

}
