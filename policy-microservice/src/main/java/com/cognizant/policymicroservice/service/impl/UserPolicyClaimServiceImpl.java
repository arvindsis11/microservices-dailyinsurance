package com.cognizant.policymicroservice.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.policymicroservice.exception.InvalidClaimException;
import com.cognizant.policymicroservice.exception.InvalidPolicyException;
import com.cognizant.policymicroservice.feign.WalletClient;
import com.cognizant.policymicroservice.model.PolicyMaster;
import com.cognizant.policymicroservice.model.Transaction;
import com.cognizant.policymicroservice.model.UserPolicyClaim;
import com.cognizant.policymicroservice.repository.PolicyMasterRepository;
import com.cognizant.policymicroservice.repository.UserPolicyClaimRepository;
import com.cognizant.policymicroservice.service.UserPolicyClaimService;

@Service
public class UserPolicyClaimServiceImpl implements UserPolicyClaimService {

	@Autowired
	UserPolicyClaimRepository userPolicyClaimRepo;

	@Autowired
	WalletClient walletClient;

	@Autowired
	PolicyMasterRepository policyMasterRepository;

	private static final Logger logger = LoggerFactory.getLogger(UserPolicyClaimServiceImpl.class);

	@Override
	public List<UserPolicyClaim> viewClaim(String username) {
		List<UserPolicyClaim> claims = userPolicyClaimRepo.findAllByUsername(username);// handle null pointer exception
																						// here
		logger.debug("View all claims");
		return claims;
	}

	// for updating the claim created with null values during policy creation fix
	// me--fix with long id
	@Transactional
	@Override
	public UserPolicyClaim updateClaim(Long id, UserPolicyClaim newclaim, String token) throws InvalidClaimException {// fix
																														// me
																														// <id>
		logger.info("updating claim db");
		Optional<PolicyMaster> policyObj = policyMasterRepository.findById(id);
		Optional<UserPolicyClaim> userClaim = userPolicyClaimRepo.findById(id);// fix date validation
		double policy_coverage = policyObj.get().getPolicy_coverage();
		@SuppressWarnings("deprecation") // for validating same day or not
		Date policy_date = userClaim.get().getPurchase_dttm();
		Date claim_date = newclaim.getClaim_dttm();
		double claim_amount = newclaim.getClaim_amount();
		if (userClaim.isPresent() && policyObj.isPresent()) {
			logger.info("policy embded object is present in userclaim");
			if (claim_date.after(policy_date)) {
				if (claim_amount <= policy_coverage && claim_amount > 0) {
					String claim_status_success = "Sucess";
					Transaction transObj = new Transaction();
					transObj.setUsername(policyObj.get().getUsername());
					transObj.setAmount(newclaim.getClaim_amount());
					double balance = walletClient.claimDeposit(token, transObj);
					return userClaim.map(data -> {
						data.setClaim_amount(newclaim.getClaim_amount());
						data.setClaim_status(claim_status_success);
						//data.setPurchase_dttm(newclaim.getPurchase_dttm());
						data.setClaim_dttm(newclaim.getClaim_dttm());
						return userPolicyClaimRepo.save(data);
					}).orElseGet(() -> {
						logger.info("CLAIM IS NOT present in userclaim");
						throw new InvalidClaimException("claim not found!");
					});
				} else {
					throw new InvalidClaimException("claim date  should not be same day policy applied");
				}

			} else {
				throw new InvalidClaimException("claim amount should not be greater then policy coverage");
			}
		} else {
			throw new InvalidPolicyException("requested claim does not match with policy!");
		}

	}

	// refer to this for showing custom status
//	@Override
//	public ResponseEntity<Student> deleteStudent(Long id) {
//		Optional<Student> student = studentRepo.findById(id);
//		if (student.isPresent()) {
//			studentRepo.deleteById(id);
//			return ResponseEntity.ok().body(student.get());
//		}
//		return ResponseEntity.notFound().build();
//	}

}
