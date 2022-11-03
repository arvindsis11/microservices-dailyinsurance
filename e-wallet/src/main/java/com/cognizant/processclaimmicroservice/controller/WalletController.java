package com.cognizant.processclaimmicroservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.processclaimmicroservice.entity.Policy;
import com.cognizant.processclaimmicroservice.entity.User;
import com.cognizant.processclaimmicroservice.entity.UserClaim;
import com.cognizant.processclaimmicroservice.entity.Wallet;
import com.cognizant.processclaimmicroservice.service.WalletService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/wallet")
public class WalletController {
	
	@Autowired
	WalletService walletService;
	
	@PostMapping("/addbal")
	public Wallet createWallet(@RequestBody Wallet obj) {
		return walletService.createWallet(obj);
	}
	
	@GetMapping("/getbal")
	public double getBal() {
		return walletService.getTotalBalance();
	}
	
	//@GetMapping
	
//	@PostMapping("/deposit")
//	public String deposit(Policy policyObj) {
//		walletService.deposit(policyObj.getPremium());
//		return "success/policy purchase";
//	}
	
//	@PutMapping("/claim")
//	public String raiseClaim() {
//		
//		walletService.deposit(getBal());
//		return "success/claim";
//		
//	}
	@PostMapping("/user")
	public User createUser(@RequestBody User useObj) {
		return walletService.createUser(useObj);
	}
	
	@PostMapping("/policy")
	public double createPolicy(@RequestBody Policy polObj) {
		return walletService.createPolicy(polObj);
		
	}
	
	@PutMapping("/claim/{id}")
	public double raiseClaim(@PathVariable(value="id") Long id,@RequestBody UserClaim useClaimObj) {
		
		return walletService.createClaim(id ,useClaimObj);
	}
	
	@PostMapping("/policy-withdraw")
	public double policyWithdraw1(@RequestBody Policy polObj) {
		return walletService.policyWithdraw(polObj.getPremium());
	}
	
	
	
}
