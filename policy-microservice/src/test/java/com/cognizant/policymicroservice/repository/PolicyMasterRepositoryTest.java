package com.cognizant.policymicroservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.policymicroservice.model.PolicyMaster;

@DataJpaTest
class PolicyMasterRepositoryTest {

	@MockBean
	PolicyMasterRepository repo;
	private 
	@Test
	void testFindAll() {
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "Health", 100, 1000, "user");
		when(repo.findAll()).thenReturn(Stream.of(pObj).collect(Collectors.toList()));
		assertThat(repo.findAll()).isNotNull();
		
	}
	
	@Test
	void testSave() {
		PolicyMaster pObj = new PolicyMaster(1L, "POL_1", "Health", 100, 1000, "user");
		when(repo.save(pObj)).thenReturn(pObj);
		//assertThat(repo.findAll()).isNotNull();
		
	}

}
