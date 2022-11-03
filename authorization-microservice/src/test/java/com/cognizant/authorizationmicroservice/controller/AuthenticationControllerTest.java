package com.cognizant.authorizationmicroservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognizant.authorizationmicroservice.config.JwtTokenUtil;
import com.cognizant.authorizationmicroservice.model.JwtRequest;
import com.cognizant.authorizationmicroservice.model.UserDao;
import com.cognizant.authorizationmicroservice.model.UserDto;
import com.cognizant.authorizationmicroservice.service.JwtUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

//fixme null

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
	
	@SuppressWarnings("deprecation")
		@BeforeEach
		public void init() {
			MockitoAnnotations.initMocks(this);
		}
		@Autowired
		private MockMvc mockMvc;
		@MockBean
		private JwtUserDetailsService userDetailsService;
		@MockBean
		private JwtTokenUtil jwtTokenUtil;
		@MockBean
		private AuthenticationManager authenticationManager;
		@Test
		void testBadRequestGenerateToken() throws Exception {
			this.mockMvc.perform(post("/authenticate")).andExpect(status().isBadRequest());
		}
		//fix here
		@Test
		void testBadRequestRegi() throws Exception {
			this.mockMvc.perform(post("/register")).andExpect(status().isBadRequest());
		}
		@Test
		void testAuthorizedGenerateToken() throws Exception {
			UserDao user = new UserDao(1, "admin", "pass", "arvind", "sisodiya", "arvindsis35@gmail.com");
			UserDetails details = new org.springframework.security.core.userdetails.User(user.getUsername(),
					user.getPassword(), new ArrayList<>());
			when(jwtTokenUtil.generateToken(details)).thenReturn("token");
			when(userDetailsService.loadUserByUsername("admin")).thenReturn(details);
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(new JwtRequest("admin", "pass"));
			this.mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(jsonString))
					.andExpect(status().isOk());
		}
			
		

		@Test
		void textExistingUserAuthenticate() throws Exception {
		UserDao user = new UserDao(1, "admin", "pass", "arvind", "sisodiya", "arvindsis35@gmail.com");
		UserDetails details = new org.springframework.security.core.userdetails.User(user.getUsername(),
		user.getPassword(), new ArrayList<>());
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
		"admin", "password");
		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("admin", "password")))
		.thenReturn(usernamePasswordAuthenticationToken);
		when(userDetailsService.loadUserByUsername("admin")).thenReturn(details);
		when(jwtTokenUtil.getUsernameFromToken("token")).thenReturn("admin");
		when(jwtTokenUtil.generateToken(details)).thenReturn("token");
		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/authenticate").contentType(MediaType.APPLICATION_JSON)
		.content(mapper.writeValueAsString(new JwtRequest("admin", "pass")))).andExpect(status().isOk());

		}

		@Test
		void textExistingUserAuthorize() throws Exception {
		UserDao user = new UserDao(1, "admin", "pass", "arvind", "sisodiya", "arvindsis35@gmail.com");
		UserDetails details = new org.springframework.security.core.userdetails.User(user.getUsername(),
		user.getPassword(), new ArrayList<>());
		when(userDetailsService.loadUserByUsername("admin")).thenReturn(details);
		when(jwtTokenUtil.getUsernameFromToken("token")).thenReturn("admin");
		mockMvc.perform(MockMvcRequestBuilders.post("/authorize").header("Authorization", "Bearer token"))
		.andExpect(status().isOk());

		}

		@Test
		void textNullTokenAuthorize() throws Exception {
		UserDao user = new UserDao(1, "admin", "pass", "arvind", "sisodiya", "arvindsis35@gmail.com");
		UserDetails details = new org.springframework.security.core.userdetails.User(user.getUsername(),
		user.getPassword(), new ArrayList<>());
		when(userDetailsService.loadUserByUsername("admin")).thenReturn(details);
		when(jwtTokenUtil.getUsernameFromToken("token")).thenReturn("admin");
		mockMvc.perform(MockMvcRequestBuilders.post("/authorize").header("Authorization", "")
		.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		}



}
