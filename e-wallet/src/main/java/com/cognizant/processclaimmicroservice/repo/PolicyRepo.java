package com.cognizant.processclaimmicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.processclaimmicroservice.entity.Policy;

@Repository
public interface PolicyRepo extends JpaRepository<Policy, Long>{

}
