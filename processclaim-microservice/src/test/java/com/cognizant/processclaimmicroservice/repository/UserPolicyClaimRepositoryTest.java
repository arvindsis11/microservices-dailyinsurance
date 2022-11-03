package com.cognizant.processclaimmicroservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.processclaimmicroservice.model.UserPolicyClaim;

@DataJpaTest
class UserPolicyClaimRepositoryTest {
	
	@MockBean
	UserPolicyClaimRepository repo;

	@Test
	void testSaveClaim() {

		UserPolicyClaim claim = new UserPolicyClaim(1L, new Date(), 500, "approved", new Date(), "user");

		when(repo.save(claim)).thenReturn(claim);
		assertThat(repo.save(claim)).isEqualTo(claim);

	}

}
