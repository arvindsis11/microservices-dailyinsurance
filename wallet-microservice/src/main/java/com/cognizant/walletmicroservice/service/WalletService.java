package com.cognizant.walletmicroservice.service;

import java.util.List;

import com.cognizant.walletmicroservice.model.Wallet;

public interface WalletService {
	//some methodes are to be used as test
	
	public double getTotalBalance(String username);
	
	public Wallet createWallet(Wallet walletObj);
	
	public List<Wallet> findAllWallets(String username);

	//utility methods for transaction management
	public double withdraw(double amount,String username);

	public double deposit(double amount,String username);

	//method to be used by policy-microservice
	double policyWithdraw(double amount,String username);

	//method to be used by processclaim-microservice
	double claimDeposit(double amount,String username);

}
