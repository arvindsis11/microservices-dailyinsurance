package com.cognizant.processclaimmicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.processclaimmicroservice.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long>{

}
