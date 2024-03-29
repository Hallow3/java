package soft.brayant.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import soft.brayant.entities.Role;
import soft.brayant.entities.User;
import soft.brayant.repository.UserRepository;
import soft.brayant.web.dto.UserRegistrationDto;

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
	public User save(UserRegistrationDto userRegistrationDto) {
		User user = new User(userRegistrationDto.getFirstName(), userRegistrationDto.getLastName(),userRegistrationDto.getEmail(),bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()) ,Arrays.asList(new Role("ROLE_USER")));
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
