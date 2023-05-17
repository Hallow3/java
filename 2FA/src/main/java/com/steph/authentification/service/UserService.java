package com.steph.authentification.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.steph.authentification.entity.User;
import com.steph.authentification.web.dto.UserRegistrationDto;


public interface UserService extends UserDetailsService{
	
    List<User> getAllUsers();
	
	User getByEmail(String email);
	
	User getById(Long id);
	
	void deleteUser(Long id);
	
	User editUser(User user);
	
	User save(UserRegistrationDto userRegistrationDto);
}
