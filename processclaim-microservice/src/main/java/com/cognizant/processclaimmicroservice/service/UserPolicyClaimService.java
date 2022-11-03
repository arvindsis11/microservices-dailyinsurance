package com.cognizant.processclaimmicroservice.service;

import com.cognizant.processclaimmicroservice.model.UserPolicyClaim;

public interface UserPolicyClaimService {
	//for testing purpose only
		public UserPolicyClaim updateClaim(Long id,UserPolicyClaim claim,String token);
	

}
