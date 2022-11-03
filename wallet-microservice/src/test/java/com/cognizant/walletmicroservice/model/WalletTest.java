package com.cognizant.walletmicroservice.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;

class WalletTest {

	Wallet obj = new Wallet();
	
	@Test
	void testGetId() {
		obj.setId(1L);
		assertThat(obj.getId()).isEqualTo(1L);
	}

	@Test
	void testGetMode() {
		obj.setMode("paytm");
		assertThat(obj.getMode()).isEqualTo("paytm");
	}

	@Test
	void testGetAmount() {
		obj.setAmount(100);
		assertThat(obj.getAmount()).isEqualTo(100);
	}

	

	@Test
	void testWalletLongStringDouble() {
		obj = new Wallet(1L,"pay",100, "user", new Date());
		assertThat(obj).isEqualTo(new Wallet(1L,"pay",100, "user", new Date()));
	}

	@Test
	void testWallet() {
		obj = new Wallet();
		assertThat(obj).isEqualTo(new Wallet());
	}

}
