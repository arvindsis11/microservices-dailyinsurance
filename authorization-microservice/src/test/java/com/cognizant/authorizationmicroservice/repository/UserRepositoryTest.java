package com.cognizant.authorizationmicroservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.authorizationmicroservice.model.UserDao;
import com.cognizant.authorizationmicroservice.model.UserDto;

@DataJpaTest
public class UserRepositoryTest {
	
	@MockBean
	private UserRepository repo;
	
//	@Test
//	void testUserRepositoryFindByname()
//	{
//		UserDao user = new UserDao(1, "admin" ,"password", "Arvind", "Sisodiya", "arvindsis35@gmail.com");
//		Optional<UserDao> findByUsername = repo.findByUsername("admin");
//		when(repo.findByUsername("admin")).thenReturn(findByUsername);
//		assertThat(repo.findByUsername("admin")).isEqualTo((user));
//				
//	}
//	
//	@Test
//	void textUserDaoSave() throws Exception {
//	UserDto user = new UserDto("admin", "pass", "arvind", "sisodiya", "arvindsis35@gmail.com");
//	UserDao newUser = new UserDao();
//	newUser.setUsername(user.getUsername());
//	newUser.setPassword(user.getPassword());
//	newUser.setFirstname(user.getFirstname());
//	newUser.setLastname(user.getLastname());
//	newUser.setEmailid(user.getEmailid());
//	when(repo.save(newUser)).thenReturn(newUser);
//	}

}
