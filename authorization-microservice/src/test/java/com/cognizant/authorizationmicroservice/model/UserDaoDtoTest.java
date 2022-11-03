package com.cognizant.authorizationmicroservice.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDaoDtoTest {
	
	private UserDao user1;
	private UserDto user2;
	
	@Test
	void testUserDao() throws Exception
	{
		user1 = new UserDao();
		user1 = new UserDao(1, "user", "pass","Arvind", "Sisodiya", "arvindsis35@gmail.com");
		user1.setId(1);
		user1.setFirstname("Arvind");
		user1.setLastname("sisodiya");
		user1.setEmailid("arvindsis35@gmail.com");
		user1.setUsername("test");
		user1.setPassword("test");
		user1.getId();
		user1.getFirstname();
		user1.getLastname();
		user1.getEmailid();
		user1.getUsername();
		user1.getPassword();
		
	}
	
	@Test
	void testUserDto() throws Exception
	{
		user2 = new UserDto();
		user2 = new UserDto("user", "pass","Arvind", "Sisodiya", "arvindsis35@gmail.com");
		user2.setFirstname("Arvind");
		user2.setLastname("sisodiya");
		user2.setEmailid("arvindsis35@gmail.com");
		user2.setUsername("test");
		user2.setPassword("test");
		user2.getFirstname();
		user2.getLastname();
		user2.getEmailid();
		user2.getUsername();
		user2.getPassword();
		
	}

}
