package com.cognizant.walletmicroservice.model;

import java.time.LocalDateTime;
import java.util.Date;

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
@Table(name = "wallet")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description="Model value that stores the wallet details")
public class Wallet {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes ="primary key Id")
	private Long id;
	
	@Column
	@ApiModelProperty(notes="mode of payment for the wallet")
	private String mode;
	
	@Column
	@ApiModelProperty(notes="amount of balance to be added into wallet")
	private double amount;
	
	@Column
	@ApiModelProperty(notes="specific user's data")
	private String username;
	

	@Column
	@ApiModelProperty(notes="keeping transaction time")
	private Date transTime;

}
