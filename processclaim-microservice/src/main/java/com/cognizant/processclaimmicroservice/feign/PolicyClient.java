package com.cognizant.processclaimmicroservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.processclaimmicroservice.model.UserPolicyClaim;

import io.swagger.annotations.ApiOperation;

@FeignClient(name = "policy-microservice", url = "http://localhost:8082/policy")
public interface PolicyClient {
	@PutMapping("/update-claim/{id}")
	@ApiOperation(notes = "returns UserPolicyClaim object, details", value = "display the claim created")
	public UserPolicyClaim updateUserPolicyClaim(@PathVariable(value = "id") Long id, @RequestBody UserPolicyClaim claim,@RequestHeader("Authorization") String token);

}
