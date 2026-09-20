package com.codestart.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestart.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	boolean existsByEmail(String email);

}
