package com.cognizant.processclaimmicroservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cognizant.processclaimmicroservice.entity.Policy;
import com.cognizant.processclaimmicroservice.entity.User;
import com.cognizant.processclaimmicroservice.entity.UserClaim;
import com.cognizant.processclaimmicroservice.entity.Wallet;

public interface WalletService {
	// some methodes are to be used as test

	public double getTotalBalance();
	
	//public double getCurrentBalance();

	public double deposit(double amount);

	public Wallet createWallet(Wallet walletObj);

	public List<Wallet> findAllWallets();

	public double withdraw(double amount);

	public double createPolicy(Policy policyObj);

	public User createUser(User userObj);

	public double createClaim(Long id,UserClaim userClaimObj);

	public double policyWithdraw(double amount);

}
