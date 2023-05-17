package soft.brayant.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import soft.brayant.entities.User;
import soft.brayant.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	
	User save(UserRegistrationDto userRegistrationDto);
}
