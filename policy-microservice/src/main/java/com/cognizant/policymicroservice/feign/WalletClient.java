package com.cognizant.policymicroservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.policymicroservice.model.Transaction;

@FeignClient(name="wallet-microservice",url="http://localhost:8081/wallet")
public interface WalletClient {
	
	@PostMapping("/policy-balance")
	public double policyWithdraw1(@RequestHeader("Authorization") String token, @RequestBody Transaction transObj);
	
	@PostMapping("/claim-balance")
	public double claimDeposit(@RequestHeader("Authorization") String token, @RequestBody Transaction transObj);
	
	@GetMapping("/total-balance/{user}")
	public double getTotalBalance(@RequestHeader("Authorization") String token,@PathVariable(value = "user") String user);
	
	

}
