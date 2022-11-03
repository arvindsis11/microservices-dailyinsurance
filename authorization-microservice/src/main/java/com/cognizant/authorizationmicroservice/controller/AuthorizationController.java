package com.cognizant.authorizationmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.authorizationmicroservice.config.JwtTokenUtil;
import com.cognizant.authorizationmicroservice.model.JwtRequest;
import com.cognizant.authorizationmicroservice.model.JwtResponse;
import com.cognizant.authorizationmicroservice.model.UserDto;
import com.cognizant.authorizationmicroservice.repository.UserRepository;
import com.cognizant.authorizationmicroservice.service.JwtUserDetailsService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
@Slf4j
public class AuthorizationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	UserRepository userRepo;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	@ApiOperation(notes="Returns token to authenticate the microservices", value="authentication")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ApiOperation(notes="saving the details of new user in database", value="register user")
	public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@PostMapping("/authorize")
	@ApiOperation(notes="checking the authorized user with the help of token", value="authorized user")
	public boolean authorize(@RequestHeader("Authorization") String token) {
		String authToken = null;
		String user = null;
		if(token!=null && token.startsWith("Bearer ")) {
			authToken = token.substring(7);
			user = jwtTokenUtil.getUsernameFromToken(authToken);
		}
		
		if(user == null) {
			return false;
		}
		
		return true;
	}
}


