package com.cognizant.policymicroservice.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cognizant.policymicroservice.exception.AuthorizationException;
import com.cognizant.policymicroservice.exception.InvalidClaimException;
import com.cognizant.policymicroservice.exception.InvalidPolicyException;
import com.cognizant.policymicroservice.model.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler({ InvalidClaimException.class })
	public ErrorResponseDTO InvalidClaimException(Exception exception, HttpServletRequest request) {
		return new ErrorResponseDTO(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
				exception.getMessage(), request.getRequestURI());
	}

	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler({ InvalidPolicyException.class })
	public ErrorResponseDTO InvalidPolicyException(Exception exception, HttpServletRequest request) {
		return new ErrorResponseDTO(new Date(), HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(),
				exception.getMessage(), request.getRequestURI());
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ AuthorizationException.class })
	public ErrorResponseDTO AuthorizationException(Exception exception, HttpServletRequest request) {
		return new ErrorResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
				exception.getMessage(), request.getRequestURI());
	}

}
