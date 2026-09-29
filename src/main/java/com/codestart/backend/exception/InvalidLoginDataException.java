package com.codestart.backend.exception;

public class InvalidLoginDataException extends RuntimeException {
	
	public InvalidLoginDataException(String message) {
        super(message);
    }

}
