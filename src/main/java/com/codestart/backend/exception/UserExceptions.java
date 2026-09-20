package com.codestart.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class UserExceptions {
	
	@ExceptionHandler(EmailExistsException.class)
	
	public ResponseEntity<String> handle(EmailExistsException ex)
	{
		return ResponseEntity.status(409).body(ex.getMessage());
	}
	
	@ExceptionHandler(InvalidRegistrationDataException.class)
		
		public ResponseEntity<String> handle(InvalidRegistrationDataException ex)
		{
			return ResponseEntity.status(400).body(ex.getMessage());
		}


}
