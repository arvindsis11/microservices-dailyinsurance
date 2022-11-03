package com.cognizant.authorizationmicroservice.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description ="Model for the jwt Response for authorization")
public class JwtResponse implements Serializable {

	@ApiModelProperty(notes ="Serial version Id")
	private static final long serialVersionUID = -8091879091924046844L;
	
	@ApiModelProperty(notes="JWT token")
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}