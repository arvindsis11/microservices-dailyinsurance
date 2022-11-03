package com.cognizant.walletmicroservice.exception;

public class InvalidWalletBalanceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidWalletBalanceException(String message) {
		super(message);
	}

}
