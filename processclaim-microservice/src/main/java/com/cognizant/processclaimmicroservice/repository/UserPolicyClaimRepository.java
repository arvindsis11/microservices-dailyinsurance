package com.cognizant.processclaimmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.processclaimmicroservice.model.UserPolicyClaim;

@Repository
public interface UserPolicyClaimRepository  extends JpaRepository<UserPolicyClaim, Long>{

}
