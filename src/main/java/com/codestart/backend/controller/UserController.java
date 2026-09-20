package com.codestart.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codestart.backend.dto.UserRegistrationRequest;
import com.codestart.backend.service.UserService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired 
	private UserService userService;
	
	@PostMapping("/register")
	
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "User registered successfully"
	    ),
	    @ApiResponse(
	        responseCode = "409",
	        description = "Email already exists"
	    ),
	    @ApiResponse(
		        responseCode = "400",
		        description = "Name and email should not be empty, and password must be at least 8 characterss"
		    )
	})
	public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest dto) 
	{
		userService.registerUser(dto);
		return ResponseEntity.status(201).body("User registered successfully");
	}

}
