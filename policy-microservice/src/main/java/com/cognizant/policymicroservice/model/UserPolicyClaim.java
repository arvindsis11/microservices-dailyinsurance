package com.cognizant.policymicroservice.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "UserPolicyclaim")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="Model object that stores the claim details")
public class UserPolicyClaim {
	
	@Id
	@Column()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("id of consumer")
	private Long user_id;
	
	@Column
	@ApiModelProperty("purchase date in yyyy-MM-ddTHH:mm:ss format")
	private Date purchase_dttm;
	//DATETIME - format: YYYY-MM-DD HH:MI:SS fix later
	@Column
	@ApiModelProperty("claim amount")
	private double claim_amount;
	
	@Column
	@ApiModelProperty("date time of claim")
	private String claim_status;//fix me in case of error
	
	@Column
	@ApiModelProperty("date time of claim")
	private Date claim_dttm;
	
	@Column
	@ApiModelProperty("date time of claim")
	private String username;
	
//	@OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "p_id", nullable = false)
//    private Consumer consumer;
//	@OneToOne(cascade = CascadeType.MERGE)
//	@JoinColumn(name="user_id", referencedColumnName="user_id",nullable=true)
//	private Consumer consumer;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="policy_id", referencedColumnName="policy_id",nullable=true)
	private PolicyMaster policymaster;
	

	
	 
}