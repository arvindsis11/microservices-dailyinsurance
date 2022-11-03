package com.cognizant.authorizationmicroservice.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description ="Model for the jwt request for authorization")
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	@ApiModelProperty(notes="Name of User")
	private String username;
	@ApiModelProperty(notes ="Password of the User")
	private String password;
	
	//default constructor for JSON Parsing
	public JwtRequest()
	{
	}

	public JwtRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}