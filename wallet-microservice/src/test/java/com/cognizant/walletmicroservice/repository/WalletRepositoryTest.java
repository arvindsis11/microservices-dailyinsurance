package com.cognizant.walletmicroservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.walletmicroservice.model.Wallet;

@DataJpaTest
class WalletRepositoryTest {

	@MockBean
	private WalletRepository repo;
	
	private Wallet walletObj;
	@BeforeEach
	void setup() {
		walletObj = new Wallet(1L,"Paytm",1000, "user", new Date());
	}
	
	@AfterEach
	void tearDown() {
		repo.deleteAll();
	}
	
	@Test
	void testFindAll() {
		when(repo.findAll()).thenReturn(Stream.of(walletObj,new Wallet(2L,"phonePe",100, "user", new Date())).collect(Collectors.toList()));
		assertThat(repo.findAll().size()).isEqualTo(Stream.of(walletObj,new Wallet(2L,"phonePe",100, "user", new Date())).collect(Collectors.toList()).size());
	}

	@Test
	void testSave() {
		Wallet newWallet = new Wallet();
		newWallet.setId(walletObj.getId());
		newWallet.setAmount(walletObj.getAmount());;
		newWallet.setMode(walletObj.getMode());
		when(repo.save(walletObj)).thenReturn(newWallet);
	}

}
