package com.cognizant.processclaimmicroservice.entity;

import javax.persistence.CascadeType;
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
@Table(name = "policy")
@AllArgsConstructor
@NoArgsConstructor
public class Policy {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long policy_id;
	private String p_name;
	private double premium;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "policyObj")
	private UserClaim userClaim;
	
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_id", nullable = true)
//	private Policy policyObj;

}
