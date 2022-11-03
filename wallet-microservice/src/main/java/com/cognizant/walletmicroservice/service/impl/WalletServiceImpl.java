package com.cognizant.walletmicroservice.service.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.walletmicroservice.exception.InvalidWalletBalanceException;
import com.cognizant.walletmicroservice.model.Transaction;
import com.cognizant.walletmicroservice.model.Wallet;
import com.cognizant.walletmicroservice.repository.TransactionRepository;
import com.cognizant.walletmicroservice.repository.WalletRepository;
import com.cognizant.walletmicroservice.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	WalletRepository walletRepo;

	@Autowired
	TransactionRepository transactionRepo;

	private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

	@Override
	public Wallet createWallet(Wallet walletObj) throws InvalidWalletBalanceException {
		logger.info("Add balance to wallet");
		if (walletObj.getAmount() > 0 && walletObj.getAmount() <= 10000) {
			double current = 0;
			double finalBalance = 0;
			Optional<Transaction> currentObj = transactionRepo.findByUsername(walletObj.getUsername());
			if (currentObj.isPresent()) {
				current = transactionRepo.findByUsername(walletObj.getUsername()).get().getAmount();
				finalBalance = current + walletObj.getAmount();
				currentObj.get().setAmount(finalBalance);
			} else {
				Transaction trObj = new Transaction();
				trObj.setUsername(walletObj.getUsername());
				trObj.setAmount(walletObj.getAmount());
				transactionRepo.save(trObj);
			}

			logger.info("Balance Added successfully!");
			walletObj.setTransTime(new Date());;
			return walletRepo.save(walletObj);
		} else {
			throw new InvalidWalletBalanceException("amount can't be less then 0 or more then 10,000,numeric");
		}

	}

	@Override
	public double getTotalBalance(String username) {

		double current = transactionRepo.findByUsername(username).get().getAmount();
		return current;
	}

	@Override
	public List<Wallet> findAllWallets(String username) {
		List<Wallet> walletes = walletRepo.findAllByUsername(username);//handle null pointer exception here
		logger.debug("view all available wallets");
		System.err.println(walletes);
		return walletes;
	}

	@Override
	public double deposit(double amount,String username) {
		double current = getTotalBalance(username);
		double finalamount = 0;
		if (amount > 0 && amount <= 10000) {
			finalamount = current + amount;
			Optional<Transaction> bal = transactionRepo.findByUsername(username);
			logger.info("Balance deposited successfully!");
			bal.get().setAmount(finalamount);
		}
		return finalamount;

	}

	@Override
	public double withdraw(double amount,String username) {
		double current = getTotalBalance(username);
		double finalamount = 0;
		if (amount < current) {
			finalamount = current - amount;
			Optional<Transaction> bal = transactionRepo.findByUsername(username);
			bal.get().setAmount(finalamount);
			logger.info("Balance deducted successfully!");
		}
		return finalamount;
	}

	// policy service will use this method to update wallet after purchase
	@Override
	public double policyWithdraw(double amount,String username) {
		double finalBalance = withdraw(amount,username);
		System.err.println("POlicy amount:policyWithdraw() " + finalBalance);//for testing
		Transaction trObj = new Transaction();
		trObj.setUsername(username);
		trObj.setAmount(finalBalance);
		transactionRepo.save(trObj);
		finalBalance = transactionRepo.findByUsername(username).get().getAmount();
		return finalBalance;
	}

	// process claim microservice will use this method to deposit balance
	@Override
	public double claimDeposit(double amount,String username) {
		double finalBalance = deposit(amount,username);
		System.err.println("Claim amount:ClaimDeposit() " + finalBalance);//for testing
		Transaction trObj = new Transaction();
		trObj.setUsername(username);
		trObj.setAmount(finalBalance);
		transactionRepo.save(trObj);
		finalBalance = transactionRepo.findByUsername(username).get().getAmount();
		return finalBalance;
	}
	//for checking if the value is numeric or not
//	public static boolean isNumeric(String str) { 
//		  try {  
//		    Double.parseDouble(str);  
//		    return true;
//		  } catch(NumberFormatException e){  
//		    return false;  
//		  }  
//		}
}
