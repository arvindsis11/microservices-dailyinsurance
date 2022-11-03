package com.cognizant.policymicroservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.policymicroservice.model.UserPolicyClaim;

@Repository
public interface UserPolicyClaimRepository extends JpaRepository<UserPolicyClaim, Long>{
	
	List<UserPolicyClaim> findAllByUsername(String username);
	Optional<UserPolicyClaim> findByUsername(String username);

}
