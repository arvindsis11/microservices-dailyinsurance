package com.cognizant.processclaimmicroservice.service.ImpL;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.processclaimmicroservice.entity.Policy;
import com.cognizant.processclaimmicroservice.entity.Transaction;
import com.cognizant.processclaimmicroservice.entity.User;
import com.cognizant.processclaimmicroservice.entity.UserClaim;
import com.cognizant.processclaimmicroservice.entity.Wallet;
import com.cognizant.processclaimmicroservice.repo.PolicyRepo;
import com.cognizant.processclaimmicroservice.repo.TransactionRepo;
import com.cognizant.processclaimmicroservice.repo.UserClaimRepo;
import com.cognizant.processclaimmicroservice.repo.UserRepo;
import com.cognizant.processclaimmicroservice.repo.WalletRepository;
import com.cognizant.processclaimmicroservice.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	WalletRepository walletRepo;
	
	@Autowired
	PolicyRepo policyRepo;
	
	@Autowired
	UserClaimRepo userClaimRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	TransactionRepo transactionRepo;
	
	

	private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

	//for creating wallet and adding it to new table
	@Override
	public Wallet createWallet(Wallet walletObj) {
		double current =0;
		double finalBalance=0;
		Optional<Transaction> currentObj = transactionRepo.findById(1L);
		if(currentObj.isPresent()) {
			current = transactionRepo.findById(1L).get().getAmount();
			finalBalance = current+walletObj.getAmount();
			currentObj.get().setAmount(finalBalance);
		}
		else {
			Transaction trObj =  new Transaction();
			trObj.setId(1L);
			trObj.setAmount(walletObj.getAmount());
			transactionRepo.save(trObj);
		}
		
		return walletRepo.save(walletObj);
	}

//	public Transaction createTransaction() {
//		return 
//	}
	@Override
	public double getTotalBalance() {
		
		
		double current = transactionRepo.findById(1L).get().getAmount();
		return current;
	}

	@Override
	public double deposit(double amount) {
		double current = getTotalBalance();
		double finalamount =  0;
		if(amount>0&&amount<=10000) {
			finalamount = current + amount;
			Optional<Transaction> bal = transactionRepo.findById(1L);
			bal.get().setAmount(finalamount);	
		}
		return finalamount;
		
	}
	
	@Override
	public double withdraw(double amount) {
		double current = getTotalBalance();
		double finalamount =  0;
		if(amount<current) {
			finalamount = current - amount;
			Optional<Transaction> bal = transactionRepo.findById(1L);
			bal.get().setAmount(finalamount);	
		}
		return finalamount;
	}
	//backup
	@Override
	public double policyWithdraw(double amount) {
		double finalBalance=withdraw(amount);
		System.err.println("POlicy amount:policyWithdraw() "+finalBalance);
		Transaction trObj =  new Transaction();
		trObj.setId(1L);
		trObj.setAmount(finalBalance);
		transactionRepo.save(trObj);		
		finalBalance = transactionRepo.findById(1L).get().getAmount();
		return amount;
	}

	@Override
	public List<Wallet> findAllWallets() {

		logger.debug("view all available wallets");
		return walletRepo.findAll();
	}

	
	@Override
	public double createPolicy(Policy policyObj) {
		double finalBalance=withdraw(policyObj.getPremium());
		System.err.println("POlicy amount: "+policyObj.getPremium());
	    policyRepo.save(policyObj);
	    UserClaim userClaimObj = new UserClaim();
	    userClaimObj.setClaim_id(policyObj.getPolicy_id());
	    userClaimObj.setPolicyObj(policyObj);
	    userClaimRepo.save(userClaimObj);
	    
	    return finalBalance;
	}

	@Override
	public User createUser(User userObj) {
		
		return userRepo.save(userObj);
	}

	@Override
	public double createClaim(Long id,UserClaim nuserClaimObj) {
		Optional<UserClaim> claimObj = userClaimRepo.findById(id);
		double finalBalance=0;
		if(claimObj.isPresent()) {
			finalBalance=deposit(nuserClaimObj.getClaim_amt());
			claimObj.get().setClaim_amt(nuserClaimObj.getClaim_amt());
			claimObj.get().setStatus(nuserClaimObj.getStatus());
			userClaimRepo.save(claimObj.get());
		}
		else {
			finalBalance=deposit(nuserClaimObj.getClaim_amt());
			userClaimRepo.save(nuserClaimObj);
		}
		
		return finalBalance;
	}

	



}
