package com.codestart.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codestart.backend.dto.UserLoginRequest;
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
	@Autowired
	private JwtService jwtService;
	
	
	
	
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
	
	
	public String loginUser(UserLoginRequest dto)
	{
		if(dto.getEmail().isBlank() || dto.getPassword().isBlank())
			throw new InvalidLoginDataException("Email or password should not be empyty");
		
		Optional<User> user = repo.findByEmail(dto.getEmail());
		
		if(user.isEmpty())
			throw new InvalidLoginDataException("Invalid email or password");
		
		
		if(!encoder.matches(dto.getPassword(),user.get().getPassword()))
			throw new InvalidLoginDataException("invalid email or password");
		
		else
		{
			String token = jwtService.generateToken(
			        user.get().getEmail(),
			        user.get().getId()
			);
			
			return token;
		
		}
		
			
		
				
	
	}
	
	

}
