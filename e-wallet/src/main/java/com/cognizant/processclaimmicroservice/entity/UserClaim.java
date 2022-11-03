package com.cognizant.processclaimmicroservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "userclaim")
@NoArgsConstructor
@AllArgsConstructor
public class UserClaim {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long claim_id;
	//private Long user_id;
	private double claim_amt;
	//private Date claim_date = new Date();
	private String status;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policy_id", nullable = true)
	private Policy policyObj;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private User userObj;

}

