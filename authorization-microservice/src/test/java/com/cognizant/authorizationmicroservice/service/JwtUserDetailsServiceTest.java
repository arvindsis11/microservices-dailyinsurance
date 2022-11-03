package com.cognizant.authorizationmicroservice.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cognizant.authorizationmicroservice.model.UserDao;
import com.cognizant.authorizationmicroservice.repository.UserRepository;


@SpringBootTest
public class JwtUserDetailsServiceTest {
	
	@Mock 
	UserRepository repo;
	
	@Mock 
	private PasswordEncoder bcryptEncoder;
	
	@InjectMocks
	private JwtUserDetailsService service;
	
	@BeforeEach
	void setUp() throws Exception
	{
		
	}
	
	@Test
	void loadUserByUserNameShouldThrowExceptionTest()
	{
		when(repo.findByUsername("wrongUserName")).thenReturn(null);
		assertThatThrownBy(() -> service.loadUserByUsername("wrongUserName"))
		.isInstanceOf(UsernameNotFoundException.class)
		.hasMessage("User not found with username: wrongUserName");
		verify(repo, Mockito.times(1)).findByUsername("wrongUserName");
	}
	
	
	@Test
	void loadUserByUserNameShouldUserNameTest()
	{
		Optional<UserDao> userDao = Optional.of(new UserDao(1, "Arvind", "Arvind@123", "Arvind", "Sisodiya", "arvindsis35@gmail.com"));
		when(repo.findByUsername("Arvind")).thenReturn(userDao);
		assertThat(service.loadUserByUsername("Arvind")).isNotNull();
		verify(repo, Mockito.times(1)).findByUsername("Arvind");
	}
	
	
	
	
	

}
