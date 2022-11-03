package com.cognizant.policymicroservice.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PolicyModelTest {

	PolicyMaster pobj = new PolicyMaster();

	


	@Test
	void testPolicyMasterModel() {

		pobj.setPolicy_id(1L);
		pobj.setPolicy_name("medical");
		pobj.setPolicyNameId("POL_1");
		pobj.setPolicy_premium(100);
		pobj.setPolicy_coverage(1000);
		pobj.setUsername("user");
		pobj = new PolicyMaster();
		assertThat(pobj).isEqualTo(pobj);
		pobj = new PolicyMaster(1L, "POL_1", "medical", 100, 1000, "user");
		assertThat(pobj).isEqualTo(pobj);
		assertThat(pobj.getPolicy_id()).isEqualTo(1L);
		assertThat(pobj.getPolicy_name()).isEqualTo("medical");
		assertThat(pobj.getPolicyNameId()).isEqualTo("POL_1");
		assertThat(pobj.getPolicy_premium()).isEqualTo(100);
		assertThat(pobj.getPolicy_coverage()).isEqualTo(1000);
		assertThat(pobj.getUsername()).isEqualTo("user");

	}

}
