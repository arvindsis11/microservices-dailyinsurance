package com.cognizant.policymicroservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.policymicroservice.exception.AuthorizationException;
import com.cognizant.policymicroservice.feign.AuthorizeClient;
import com.cognizant.policymicroservice.model.PolicyMaster;
import com.cognizant.policymicroservice.model.UserPolicyClaim;
import com.cognizant.policymicroservice.service.PolicyService;
import com.cognizant.policymicroservice.service.UserPolicyClaimService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/policy")
@CrossOrigin("*")
public class PolicyController {
	// for testing purpose other endpoints also declared here
	@Autowired
	PolicyService policyService;


	@Autowired
	UserPolicyClaimService userPolicyClaimService;

	@Autowired
	AuthorizeClient authorizeClient;
	// use @EnableFeignClients in main class
	private static final Logger logger = LoggerFactory.getLogger(PolicyController.class);

	// Policy service start
	@PostMapping("/purchase-policy")
	@ApiOperation(notes = "returns PolicyMaster object, details", value = "display the policy created")
	public PolicyMaster myPolicy(@RequestBody PolicyMaster policy, @RequestHeader("Authorization") String token)
			throws AuthorizationException {
		if (authorizeClient.authorize(token)) {
			logger.debug("Create Policy and claim should be null at this point");
			return policyService.createPolicy(policy, token);
		} else {
			logger.error("Access Denied");
			throw new AuthorizationException("Access Denied");
		}
	}

	// for showing all available policies
	@GetMapping("/view-policy/{user}")
	@ApiOperation(notes = "returns PolicyMaster object, details", value = "display all available policies")
	public List<PolicyMaster> viewPolicy(@RequestHeader("Authorization") String token,@PathVariable(value = "user") String user)
			throws AuthorizationException {
		if (authorizeClient.authorize(token)) {
			logger.debug("view all Policies");
			return policyService.viewPolicy(user);
		} else {
			logger.error("Access Denied");
			throw new AuthorizationException("Access Denied");
		}
	}

	// Policy service end

	// consumer service end
	// for testing purpose only --fix me
	// UserPolicyClaim service start
	@PutMapping("/update-claim/{id}")
	@ApiOperation(notes = "returns UserPolicyClaim object, details", value = "display the claim created")
	public UserPolicyClaim updateUserPolicyClaim(@PathVariable(value = "id") Long id,
			@RequestBody UserPolicyClaim claim, @RequestHeader("Authorization") String token)
			throws AuthorizationException {
		if (authorizeClient.authorize(token)) {
			logger.debug("Create claim");
			return userPolicyClaimService.updateClaim(id, claim, token);
		} else {
			logger.error("Access Denied");
			throw new AuthorizationException("Access Denied");
		}
	}

	@GetMapping("/view-claim/{user}")
	@ApiOperation(notes = "returns UserPolicyClaim object, details", value = "display all claims raised")
	public List<UserPolicyClaim> viewUserPolicyClaim(@RequestHeader("Authorization") String token,@PathVariable(value="user") String user)
			throws AuthorizationException {
		if (authorizeClient.authorize(token)) {
			logger.debug("view all claims");
			return userPolicyClaimService.viewClaim(user);
		} else {
			logger.error("Access Denied");
			throw new AuthorizationException("Access Denied");
		}
	}

}
