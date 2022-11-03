package com.cognizant.processclaimmicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.processclaimmicroservice.entity.UserClaim;

@Repository
public interface UserClaimRepo extends JpaRepository<UserClaim, Long>{

}
