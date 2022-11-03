package com.cognizant.walletmicroservice.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {
	
	private Date timestamp;
	private int  status;
	private String error;
	private String message;
	private String path;

}
