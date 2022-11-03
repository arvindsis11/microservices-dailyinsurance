package com.cognizant.policymicroservice.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.policymicroservice.exception.InvalidPolicyException;
import com.cognizant.policymicroservice.feign.WalletClient;
import com.cognizant.policymicroservice.model.PolicyMaster;
import com.cognizant.policymicroservice.model.Transaction;
import com.cognizant.policymicroservice.model.UserPolicyClaim;
import com.cognizant.policymicroservice.repository.PolicyMasterRepository;
import com.cognizant.policymicroservice.repository.TransactionRepository;
import com.cognizant.policymicroservice.repository.UserPolicyClaimRepository;
import com.cognizant.policymicroservice.service.PolicyService;
import com.cognizant.policymicroservice.service.UserPolicyClaimService;

@Service
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	WalletClient walletClient;

	@Autowired
	UserPolicyClaimService userPolicyClaimService;

	@Autowired
	PolicyMasterRepository policyMasterRepo;

	@Autowired
	UserPolicyClaimRepository userPolicyClaimRepository;

	@Autowired
	TransactionRepository transactionRepository;

	private static final Logger logger = LoggerFactory.getLogger(PolicyServiceImpl.class);

	// for creating policy and deduct balance from wallet microservice
	@Override
	public PolicyMaster createPolicy(PolicyMaster policyMaster, String token) throws InvalidPolicyException{
		logger.debug("create Policy and using WalletClient here");
		double wallet_balance = walletClient.getTotalBalance(token,policyMaster.getUsername());//fix buisiness validations
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); fix me
		if(policyMaster.getPolicy_premium()<=wallet_balance) {
			UserPolicyClaim claim = new UserPolicyClaim();// fix me
			claim.setPolicymaster(policyMaster);
			claim.setPurchase_dttm(new Date());
			claim.setUsername(policyMaster.getUsername());
			userPolicyClaimRepository.save(claim);
			Transaction transObj = new Transaction();
			transObj.setUsername(policyMaster.getUsername());;
			transObj.setAmount(policyMaster.getPolicy_premium());
			double balance = walletClient.policyWithdraw1(token, transObj);
			policyMaster.setPolicyNameId("POL_" + policyMaster.getPolicy_id());
			policyMaster.setPolicy_coverage(policyMaster.getPolicy_premium() * 100);
			return policyMasterRepo.save(policyMaster);
		} else {
			logger.info("insufficient wallet balance!");
			throw new InvalidPolicyException("insufficient wallet balance!");
		}
		
	}

	@Override
	public List<PolicyMaster> viewPolicy(String username) {
		List<PolicyMaster> policies = policyMasterRepo.findAllByUsername(username);//handle null pointer exception here
		logger.debug("View Policy Details");
		return policies;
	}

}
