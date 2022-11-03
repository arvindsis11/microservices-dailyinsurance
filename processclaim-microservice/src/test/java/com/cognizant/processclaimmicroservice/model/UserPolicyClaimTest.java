package com.cognizant.processclaimmicroservice.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;

class UserPolicyClaimTest {

	UserPolicyClaim obj = new UserPolicyClaim();
	
	@Test
	void testGetUser_id() {
		obj.setUser_id(1L);
		assertThat(obj.getUser_id()).isEqualTo(1L);
	}

	@Test
	void testGetPurchase_dttm() {
		Date date = new Date();
		obj.setPurchase_dttm(date);
		assertThat(obj.getPurchase_dttm()).isEqualTo(date);
	}

	@Test
	void testGetClaim_amount() {
		obj.setClaim_amount(2000);
		assertThat(obj.getClaim_amount()).isEqualTo(2000);
	}

	@Test
	void testGetClaim_status() {
		obj.setClaim_status("success");
		assertThat(obj.getClaim_status()).isEqualTo("success");
	}

	@Test
	void testGetClaim_dttm() {
		Date date = new Date();
		obj.setClaim_dttm(date);
		assertThat(obj.getClaim_dttm()).isEqualTo(date);
	}


	@Test
	void testUserPolicyClaim() {
		obj = new UserPolicyClaim();
		assertThat(obj).isEqualTo(obj);
	}

	@Test
	void testUserPolicyClaimLongDateDoubleStringDatePolicyMaster() {
		obj = new UserPolicyClaim(1L,new Date(),1000,"success",new Date(), "user");
		assertThat(obj).isEqualTo(obj);
	}

}
