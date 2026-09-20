package com.codestart.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codestart.backend.dto.UserRegistrationRequest;
import com.codestart.backend.entity.User;
import com.codestart.backend.exception.*;
import com.codestart.backend.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	@Autowired
	private PasswordEncoder encoder;
	
	
	public void  registerUser(UserRegistrationRequest dto){
		
		if(repo.existsByEmail(dto.getEmail()))
			throw new EmailExistsException("Email already exists");
		
		if(dto.getName().isBlank() || dto.getEmail().isBlank()|| dto.getPassword().length()<8 )
			throw new InvalidRegistrationDataException("Name and email should not be empty, and password must be at least 8 characters");
		
		String hashpwd = encoder.encode(dto.getPassword());
		
		User user = new User();
		
		user.setEmail(dto.getEmail());
		user.setName(dto.getName());
		user.setPassword(hashpwd);

	   repo.save(user);
	}
	
	

}
