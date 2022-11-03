package com.cognizant.policymicroservice.repository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.policymicroservice.model.Transaction;

@DataJpaTest
class TransactionRepositoryTest {

	@MockBean
	private TransactionRepository repo;
	
	@Test
	void testFindAll() {
		Transaction transObj = new Transaction(1000,"user");
		when(repo.findAll()).thenReturn(Stream.of(transObj).collect(Collectors.toList()));
		assertThat(repo.findAll()).isNotNull();
	}
	
	@Test
	void testTransactionSave() {
		Transaction transObj = new Transaction(1000,"user");
		Transaction obj2 = new Transaction();
		obj2.setUsername(transObj.getUsername());
		obj2.setAmount(transObj.getAmount());
		when(repo.save(obj2)).thenReturn(transObj);
	}

}