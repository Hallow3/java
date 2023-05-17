package com.brayant.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brayant.demo.entity.User;
import com.brayant.demo.service.UserService;
import com.brayant.demo.web.dto.UserRegistrationDto;

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
	public String userAccountRegistration(@RequestParam("number") String number,@RequestParam("nombre") String nombre, @ModelAttribute("user") UserRegistrationDto userRegistrationDto,Model ra) throws Exception, Exception {
		List<User> AllUser = userService.getAllUsers();
		int nombre1 = Integer.parseInt(nombre.replace(",", ""));
		int number1 = Integer.parseInt(number.replace(",", ""));
		String email = userRegistrationDto.getEmail();
		String exist = null;
		for(User u: AllUser) {
			if(u.getEmail().equalsIgnoreCase(userRegistrationDto.getEmail()))
				exist = email;
		}
		if(exist == null) {
			if(!userRegistrationDto.getConfirmPassword().equals(userRegistrationDto.getPassword())) {
				ra.addAttribute("nombre", nombre.replace(",", ""));
				ra.addAttribute("confirm", "la confirmation du mot de passe est incorrecte !");
				return "/registration2";
			}
			else {
				if(number1 == nombre1 - 1024) {
					userService.save(userRegistrationDto);
					ra.addAttribute("sucess", "vous avez bien été enregistré");
					return "/login";	
				}
				ra.addAttribute("nombre", nombre.replace(",", ""));
				ra.addAttribute("erreur", "le code de vérification est incorrect ");
				return "/registration2";
			}
				
		}else {
			ra.addAttribute("activation", "l'utilisateur avec cette addresse existe déjà !");
			return "/registration";
		}
		
	}
}
