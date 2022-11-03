package com.cognizant.policymicroservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "PolicyMaster")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Model value that stores the policy details/lookup table")
public class PolicyMaster  {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "primary key Id")
	private Long policy_id;

	@Column
	@ApiModelProperty(notes = "name of the policy")
	private String policyNameId;
	// fix me changed according to business requirement

	@Column
	@ApiModelProperty(notes = "name of the policy")
	private String policy_name;

	@Column
	@ApiModelProperty(notes = "premium amount of policy")
	private double policy_premium;

	@Column
	@ApiModelProperty(notes = "policy maximum coverage")
	private double policy_coverage;
	
	@Column
	@ApiModelProperty(notes = "policy maximum coverage")
	private String username;
	
	

//	@OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private Consumer consumer;

//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "policymaster")
//	private UserPolicyClaim userClaim;

}
