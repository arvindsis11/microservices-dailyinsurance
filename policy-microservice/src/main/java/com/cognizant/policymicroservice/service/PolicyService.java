package com.cognizant.policymicroservice.service;

import java.util.List;

import com.cognizant.policymicroservice.model.PolicyMaster;
import com.cognizant.policymicroservice.model.Transaction;

public interface PolicyService {

	public List<PolicyMaster> viewPolicy(String username);

	PolicyMaster createPolicy(PolicyMaster policyMaster, String token);

}
