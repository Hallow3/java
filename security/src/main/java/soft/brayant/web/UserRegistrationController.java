package soft.brayant.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import soft.brayant.service.UserService;
import soft.brayant.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	UserService userService;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}
	/*
	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}
	*/
	@GetMapping
	public String formRegistration(Model model) {
		model.addAttribute("user",new UserRegistrationDto());
		return "registration";
	}
	@PostMapping
	public String userAccountRegistration(@ModelAttribute("user") UserRegistrationDto userRegistrationDto) {
		userService.save(userRegistrationDto);	
		return "redirect:/registration?sucess";
	}
}
