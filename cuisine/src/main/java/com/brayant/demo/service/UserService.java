package com.brayant.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.brayant.demo.entity.User;
import com.brayant.demo.web.dto.UserRegistrationDto;


public interface UserService extends UserDetailsService{
	
    List<User> getAllUsers();
	
	User getByEmail(String email);
	
	User getById(Long id);
	
	void deleteUser(Long id);
	
	User editUser(User user);
	
	User save(UserRegistrationDto userRegistrationDto);
}
