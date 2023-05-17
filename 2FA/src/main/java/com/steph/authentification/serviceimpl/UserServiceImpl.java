package com.steph.authentification.serviceimpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.steph.authentification.entity.Role;
import com.steph.authentification.entity.User;
import com.steph.authentification.repository.UserRepository;
import com.steph.authentification.service.UserService;
import com.steph.authentification.web.dto.UserRegistrationDto;


@Service
public class UserServiceImpl implements UserService{
	
	UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public List<User> getAllUsers() {
		return  userRepository.findAll();
	}

	@Override
	public User getByEmail(String email) {
		
		return  userRepository.findByEmail(email);
	}

	@Override
	public User getById(Long id) {
		
		return  userRepository.findById(id).get();
	}

	@Override
	public void deleteUser(Long id) {
		 userRepository.deleteById(id);
		
	}

	@Override
	public User editUser(com.steph.authentification.entity.User user) {
		System.out.println("service en execution");
		return userRepository.save(user);
	}

	@Override
	public User save(UserRegistrationDto userRegistrationDto) {
		User user = new User(userRegistrationDto.getFirstName(), userRegistrationDto.getLastName(),userRegistrationDto.getAge(),userRegistrationDto.getPhone(),userRegistrationDto.getEmail(),bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()),bCryptPasswordEncoder.encode(userRegistrationDto.getConfirmPassword()),Arrays.asList(new Role("ROLE_USER")));
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("invalid username or password");
		}
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),  mapRolesToAuthorities(user.getRoles()));
		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	
	


}
