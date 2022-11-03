package com.cognizant.walletmicroservice.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.walletmicroservice.exception.InvalidWalletBalanceException;
import com.cognizant.walletmicroservice.model.Transaction;
import com.cognizant.walletmicroservice.model.Wallet;
import com.cognizant.walletmicroservice.repository.TransactionRepository;
import com.cognizant.walletmicroservice.repository.WalletRepository;

@SpringBootTest
class WalletServiceImplTest {

	@Autowired
	private WalletServiceImpl service;
	
	@MockBean
	private TransactionRepository transrepo;
	
	@MockBean
	private WalletRepository walletrepo;
	
	
	@Test
	void testCreateWalletNotnull() throws Exception{
		Wallet walletObj = new Wallet(1L,"paytm",100, "user", new Date());
		Optional<Transaction> transObj = Optional.of(new Transaction("user",100));
		when(walletrepo.save(walletObj)).thenReturn(walletObj);
		when(transrepo.findById(1L)).thenReturn(transObj);
		service.createWallet(walletObj);
		assertThat(service.createWallet(walletObj)).isNotNull();
		verify(walletrepo,Mockito.times(2)).save(walletObj);
	}
	
	@Test
	void testCreateWalletIs_exception() throws Exception{
		Wallet walletObj = new Wallet(1L,"paytm",-100, "user", new Date());
		when(walletrepo.save(walletObj)).thenReturn(walletObj);
		InvalidWalletBalanceException thrown = assertThrows(
				InvalidWalletBalanceException.class,
		           () -> service.createWallet(walletObj)
		    );

		 assertTrue(thrown.getMessage().contains("amount can't be less then 0 or more then 10,000,numeric"));
		 // assertThatThrownBy(()->service.createWallet(walletObj)).isInstanceOf(InvalidWalletBalanceException.class).hasMessage("amount can't be less then 0 or more then 10,000,numeric");

	}
	
	@Test
	void testGetTotalBalance() {
		
		Optional<Transaction> transObj = Optional.of(new Transaction("user",100));
		when(transrepo.findByUsername("user")).thenReturn(transObj);
		assertThat(service.getTotalBalance("user")).isEqualTo(100);
	}

	@Test
	void testFindAllWallets() {
		when(walletrepo.findAll()).thenReturn(Stream.of(new Wallet(1L,"paytm",100, "user", new Date()),new Wallet(2L,"phonepe",300, "user", new Date())).collect(Collectors.toList()));
		assertThat(service.findAllWallets("user").size()).isEqualTo(2);
	}

	@Test
	void testDeposit() {
		Optional<Transaction> transObj = Optional.of(new Transaction("user",100));
		when(transrepo.findByUsername("user")).thenReturn(transObj);
		assertThat(service.deposit(100, "user")).isEqualTo(200);
	}

	@Test
	void testWithdraw() {
		Optional<Transaction> transObj = Optional.of(new Transaction("user",100));
		when(transrepo.findByUsername("user")).thenReturn(transObj);
		assertThat(service.withdraw(50, "user")).isEqualTo(50);
	}

	@Test
	void testPolicyWithdraw() {
		Optional<Transaction> transObj = Optional.of(new Transaction("user",100));
		when(transrepo.findByUsername("user")).thenReturn(transObj);
		assertThat(service.policyWithdraw(20, "user")).isEqualTo(80);
		
	}

	
	@Test
	void testClaimDeposit() {
		Optional<Transaction> transObj = Optional.of(new Transaction("user",100));
		when(transrepo.findByUsername("user")).thenReturn(transObj);
		assertThat(service.claimDeposit(150, "user")).isEqualTo(250);
	}

}
