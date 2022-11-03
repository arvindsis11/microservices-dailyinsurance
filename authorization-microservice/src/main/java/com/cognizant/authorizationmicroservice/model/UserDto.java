package com.cognizant.authorizationmicroservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Model to be used for user Details")
public class UserDto {

	@ApiModelProperty(notes = "name of User")
	private String username;
	@ApiModelProperty(notes = "password of User")
	private String password;
	@ApiModelProperty(notes = "firstname of User")
	private String firstname;
	@ApiModelProperty(notes = "lastname of User")
	private String lastname;
	@ApiModelProperty(notes = "email id of User")
	private String emailid;

}