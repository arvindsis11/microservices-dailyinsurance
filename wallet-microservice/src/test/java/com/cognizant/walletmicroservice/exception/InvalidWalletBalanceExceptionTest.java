package com.cognizant.walletmicroservice.exception;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.walletmicroservice.model.Wallet;
import com.cognizant.walletmicroservice.service.impl.WalletServiceImpl;

@SpringBootTest
class InvalidWalletBalanceExceptionTest {

	@Autowired
	private WalletServiceImpl service;
	@Test
	void testInvalidWalletBalanceException() {
		Wallet walletObj = new Wallet(1L,"paytm",-100, "user", new Date());
		InvalidWalletBalanceException thrown = assertThrows(
				InvalidWalletBalanceException.class,
		           () -> service.createWallet(walletObj)
		    );

		 assertTrue(thrown.getMessage().contains("amount can't be less then 0 or more then 10,000,numeric"));
	}

}
