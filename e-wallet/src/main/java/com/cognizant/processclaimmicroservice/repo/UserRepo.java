package com.cognizant.processclaimmicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.processclaimmicroservice.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

}
