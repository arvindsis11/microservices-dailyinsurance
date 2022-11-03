package com.cognizant.policymicroservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.policymicroservice.model.PolicyMaster;

@Repository
public interface PolicyMasterRepository extends JpaRepository<PolicyMaster, Long>{
	
	List<PolicyMaster> findAllByUsername(String username);
	Optional<PolicyMaster> findByUsername(String username);

}
