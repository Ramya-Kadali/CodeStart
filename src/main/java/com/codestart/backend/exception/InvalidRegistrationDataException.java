package com.codestart.backend.exception;

public class InvalidRegistrationDataException extends RuntimeException{
	public InvalidRegistrationDataException(String message) {
        super(message);
    }

}
