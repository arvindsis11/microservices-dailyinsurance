package com.cognizant.policymicroservice.exception;

public class InvalidClaimException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidClaimException(String message) {
		super(message);
	}
	

}
