package com.cognizant.authorizationmicroservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Model to be used for storing user details")
public class UserDao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "for id")
	private long id;
	//@Column(nullable = true,unique = true)
	@Column
	@ApiModelProperty(notes = "for name")
	private String username;
	@Column
	@JsonIgnore
	@ApiModelProperty(notes = "for password")
	private String password;

	@ApiModelProperty(notes = "for firstname")
	private String firstname;
	@ApiModelProperty(notes = "for lastname")
	private String lastname;
	@ApiModelProperty(notes = "for emailid")
	private String emailid;
	

}