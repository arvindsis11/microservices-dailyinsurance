package com.cognizant.processclaimmicroservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.processclaimmicroservice.exception.AuthorizationException;
import com.cognizant.processclaimmicroservice.feign.AuthorizeClient;
import com.cognizant.processclaimmicroservice.model.UserPolicyClaim;
import com.cognizant.processclaimmicroservice.service.UserPolicyClaimService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/claim")
@CrossOrigin("*")
public class ProcessClaimController {

	@Autowired
	UserPolicyClaimService userPolicyClaimService;

	@Autowired
	AuthorizeClient authorizeClient;

	private static final Logger logger = LoggerFactory.getLogger(ProcessClaimController.class);

	@PutMapping("/update-claim/{id}")
	@ApiOperation(notes = "returns UserPolicyClaim object, details", value = "display the UserPolicyClaim created")
	public UserPolicyClaim updateClaim(@PathVariable(value = "id") Long id,
			@RequestBody UserPolicyClaim claim, @RequestHeader("Authorization") String token)
			throws AuthorizationException {
		if (authorizeClient.authorize(token)) {
			logger.debug("Create Claim");
			return userPolicyClaimService.updateClaim(id, claim, token);
		} else {
			logger.error("Access Denied");
			throw new AuthorizationException("Access Denied");
		} // logic to deposit bal into wallet
	}

}
