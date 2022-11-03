package com.cognizant.authorizationmicroservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.authorizationmicroservice.model.UserDao;

public interface UserRepository extends JpaRepository<UserDao, Integer> {
    Optional<UserDao> findByUsername(String username);
}