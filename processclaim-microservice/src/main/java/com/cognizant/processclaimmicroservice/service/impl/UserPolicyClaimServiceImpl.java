package com.cognizant.processclaimmicroservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.processclaimmicroservice.feign.PolicyClient;
import com.cognizant.processclaimmicroservice.model.UserPolicyClaim;
import com.cognizant.processclaimmicroservice.repository.UserPolicyClaimRepository;
import com.cognizant.processclaimmicroservice.service.UserPolicyClaimService;

@Service
public class UserPolicyClaimServiceImpl implements UserPolicyClaimService {

	@Autowired
	UserPolicyClaimRepository userPolicyClaimRepo;

	@Autowired
	PolicyClient policyClient;

	private static final Logger logger = LoggerFactory.getLogger(UserPolicyClaimServiceImpl.class);

	@Override
	public UserPolicyClaim updateClaim(Long id, UserPolicyClaim claim, String token) {
		logger.debug("create claim");
		UserPolicyClaim updateUserPolicyClaim = policyClient.updateUserPolicyClaim(id, claim, token);
		// userPolicyClaimRepo.save(claim);
		return userPolicyClaimRepo.save(claim);
	}

	// for updating the claim created with null values during policy creation
}