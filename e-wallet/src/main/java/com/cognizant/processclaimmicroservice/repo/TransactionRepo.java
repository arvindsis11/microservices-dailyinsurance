package com.cognizant.processclaimmicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.processclaimmicroservice.entity.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long>{

}
