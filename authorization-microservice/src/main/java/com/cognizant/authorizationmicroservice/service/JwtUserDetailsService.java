package com.cognizant.authorizationmicroservice.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.authorizationmicroservice.exception.InvalidInputException;
import com.cognizant.authorizationmicroservice.model.UserDao;
import com.cognizant.authorizationmicroservice.model.UserDto;
import com.cognizant.authorizationmicroservice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserDao> user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

		return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
				user.get().getPassword(), new ArrayList<>());
	}

	public UserDao save(UserDto user) {
		UserDao newUser = new UserDao();
		if (isValidEmailAddress(user.getEmailid())) {
			Optional<UserDao> usernm = userDao.findByUsername(user.getUsername());
			if (!usernm.isPresent()) {// for username uniqueness check!
				if (isValidPassword(user.getPassword())) {
					newUser.setUsername(user.getUsername());
					newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
					newUser.setFirstname(user.getFirstname());
					newUser.setLastname(user.getLastname());
					newUser.setEmailid(user.getEmailid());
					return userDao.save(newUser);
				} else {
					throw new InvalidInputException(
							"password must contain 8 char at least one lowercase, one upper case, one number and the special charecters");
				}
			} else {
				throw new InvalidInputException("username already exists");
			}
		} else {
			throw new InvalidInputException("please enter valid email!");
		}

	}

	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public boolean isValidPassword(String password) {
		String ePattern = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(password);
		return m.matches();
	}
}