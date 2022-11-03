package com.cognizant.policymicroservice.service;

import java.util.List;

import com.cognizant.policymicroservice.model.UserPolicyClaim;

public interface UserPolicyClaimService {
	
	//history of raised claim
	public List<UserPolicyClaim> viewClaim(String username);
	
	//put-mapping to update the claim created with null values during policy creation
	
	public UserPolicyClaim updateClaim(Long id,UserPolicyClaim claim,String token);
}
