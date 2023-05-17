package com.brayant.demo.controller;

import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.brayant.demo.api.ApiService;
import com.brayant.demo.entity.User;
import com.brayant.demo.entity.categorie;
import com.brayant.demo.entity.recette;
import com.brayant.demo.service.CategorieService;
import com.brayant.demo.service.RecetteService;
import com.brayant.demo.service.UserService;

@Controller
public class Homepage {
	//class pour crypter le mot de passe
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	//declaration des variables pour le process
	RecetteService recetteService;
	CategorieService categorieService;
	UserService userService;
	
	//declaration de la variable pour les mails
	ApiService api = new ApiService();
	
	//variable pour le nombre aleatoire 
	int nombre1 = 0;
	
	public Homepage(RecetteService recetteService, CategorieService categorieService, UserService userService) {
		super();
		this.recetteService = recetteService;
		this.categorieService = categorieService;
		this.userService = userService;
	}
	
	//mes message pour les mails
	public String messageToSend(int code) {
		return "Bonjour monsieur/madame nous vous envoyons ce mail"
				+ " parceque vous venez de vous inscrire à l'application"
				+ " AFRICA YAMOO.</br>"
				+ " afin de poursuivre votre inscription vous devez soumettre le code <h3> "
				+ code +"</h3> dans le site pour procéder à la vérification de votre adresse</br>"
				+ " merci pour votre compréhension n'hésitez pas a soumettre vos questions"
				+ " au +237698765588 à bientot.";
	}
	public String messageToSend2(int code) {
		return "Bonjour monsieur/madame nous vous envoyons ce mail"
				+ " parceque vous venez de faire une demande de modification de mot de passe pour "
				+ " AFRICA YAMOO.</br>"
				+ " afin de poursuivre votre opération, vous devez soumettre le code <h3> "
				+ code +"</h3> dans le site pour procéder à la vérification de votre adresse</br>"
				+ " merci pour votre compréhension n'hésitez pas a soumettre vos questions"
				+ " au +237698765588 à bientot.";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}
	
	//methode qui envoie le mail a l'utilisateur a l'inscription
	@PostMapping("/toconfirm")
	public String confirmMail(@ModelAttribute User user, Model model, RedirectAttributes ra) throws AddressException, MessagingException {
		if(user.getEmail() != null) {
			nombre1 = (int) new Random().nextInt(1000000 - 100000 + 1) + 100000;
			System.out.println("nombre généré: "+nombre1);
			int nombre = nombre1+1024;
			api.sendmail(user.getEmail(), messageToSend(nombre1).indent(1), "Inscription");
			model.addAttribute("User", user);
			model.addAttribute("nombre", nombre);
			return "registration2";
		}else {
			ra.addFlashAttribute("error", "veillez vous assurer que l'e-mail est valide");
			return "registration2";
		}
	}
	
	//methode qui retourne le formulaire du mail pour la recupération du mot de passe
	@GetMapping("/passwordrecuver")
	public String recuverPassword(Model model) {
		User user = new User();
		model.addAttribute("User",user);
		return "formEnterEmail";
	}

	//acceuil
	@GetMapping("/home")
	public String getAll(Model model) {
		recette recettes = new recette();
		model.addAttribute("recet",recettes);
		model.addAttribute("recette", recetteService.getAllRecettes());
		model.addAttribute("categorie", categorieService.getAllCategories());
		return "index";
	}
	
	@GetMapping("/try")
	public String essai(Model model) {
		recette recettes = new recette();
		model.addAttribute("recet",recettes);
		model.addAttribute("recettes", recetteService.getAllRecettes());
		return "try";
	}

	//acceuil
	@GetMapping("/")
	public String getAlls(Model model) {
		recette recettes = new recette();
		model.addAttribute("recet",recettes);
		model.addAttribute("recette", recetteService.getAllRecettes());
		model.addAttribute("categorie", categorieService.getAllCategories());
		return "index";
	}

	//acceuil
	@GetMapping("/africayamoo")
	public String get(Model model) {
		recette recettes = new recette();
		model.addAttribute("recet",recettes);
		model.addAttribute("recette", recetteService.getAllRecettes());
		model.addAttribute("categorie", categorieService.getAllCategories());
		return "index";
	}
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	//toutes les recettes
	@GetMapping("/recettes")
	public String getAllRecettes(Model model) {
		model.addAttribute("recettes",recetteService.getAllRecettes());
		recette recettes = new recette();
		model.addAttribute("recet",recettes);
		return "recettes";
	}
	
	@PostMapping("/search")
	public String getByName(@ModelAttribute("recet") recette recette, Model model) {
		model.addAttribute("recettes",recetteService.getByName(recette.getNameRecette()));
		model.addAttribute("categories",categorieService.getByName(recette.getNameRecette()));
		recette recettes = new recette();
		categorie cate = new categorie();
		model.addAttribute("recet",recettes);
		model.addAttribute("cate", cate);
		return "recherche";
	}
	
	//description d'une recette
	@GetMapping("recette")
	public String description(@RequestParam("id") Long id, Model model) {
		model.addAttribute("recette", recetteService.getById(id));
		String mail = recetteService.getById(id).getAuteur().getEmail();
		String auteur = ""+recetteService.getById(id).getAuteur().getFirstName()+" "+recetteService.getById(id).getAuteur().getLastName();
		model.addAttribute("auteur", auteur);
		model.addAttribute("mail", mail);
		recette recettes = new recette();
		model.addAttribute("recet",recettes);
		return "description";
	}
	
	//afficher les recettes par catégorie
	@GetMapping("/recettes/categorie")
	public String recetteCategorie(@RequestParam("id") Long id, Model model) {
		categorie cat = categorieService.getById(id);
		List<recette> recetteCat = recetteService.getByCategorie(cat);
		model.addAttribute("recettesCategorie", recetteCat);
		if(recetteCat.isEmpty()) {
			model.addAttribute("vide", "la catégorie ne possède pas encore de recettes");
		}
		recette recettes = new recette();
		model.addAttribute("recet",recettes);
		return "recetteCategorie";
	}
	
	//méthode qui va envoyer le mail a l'utilisateur souhaitant récupérer son password
	@PostMapping("/verify")
	public String verifyEmail(@ModelAttribute("User") User user, Model model,RedirectAttributes ra) throws AddressException, MessagingException{
		System.out.println("mail: "+user.getEmail());
		User u1 = userService.getByEmail(user.getEmail());
		if(u1 != null) {
			System.out.println("mail existant: "+user.getEmail());
			nombre1 = (int) new Random().nextInt(1000000 - 100000 + 1) + 100000;
			System.out.println("nombre généré: "+nombre1);
			int nombre = nombre1+1024;
			api.sendmail(user.getEmail(), messageToSend2(nombre1).indent(1), "Récupération Du Mot De Passe");
			System.out.println("code envoyé: "+nombre1);
			model.addAttribute("User", user);
			model.addAttribute("nombre", nombre);
			return "recover";
		}else {
			ra.addFlashAttribute("notFound", "veillez vous assurer que l'e-mail est valide");
			return "redirect:/passwordrecuver";
		}
	}
	
	//fonction qui retourne le formulaire de modification du mot de passe
	@PostMapping("/formpasswordrecuver")
	public String formrecuverPassword(User user, @RequestParam("nombre") String nombre, @RequestParam("number") String number,Model model, RedirectAttributes ra) {
		int nombre1 = Integer.parseInt(nombre.replace(",", ""));
		int number1 = Integer.parseInt(number.replace(",", "")); 
		System.out.println("nombre: " + nombre1 + " number: " + number1);
		if(user.getEmail() != null) {
			if(userService.getByEmail(user.getEmail()) != null) {
				System.out.println("verification des nombres");
				if(number1 == nombre1 - 1024) {
					model.addAttribute("User", userService.getByEmail(user.getEmail()));
					return "formRecuverPassword";
				}else {
					model.addAttribute("nombre", nombre.replace(",", ""));
					model.addAttribute("code", "le code de vérification est incorrect ");
					model.addAttribute("User", userService.getByEmail(user.getEmail()));
					return "recover";
				}
			}else {
				ra.addFlashAttribute("refuse", "l'utilisateur est introuvable");
				return "redirect:/login";
			}
		}else {
			ra.addFlashAttribute("refuse", "l'utilisateur est introuvable");
			return "redirect:/login";
		}
	}

	
	
	//methode qui va permettre de modifier le mot de passe 
	@PostMapping("/passwordrecuver")
	public String passwordRecuver(@ModelAttribute("User") User user, Model model, RedirectAttributes ra) {
		if(userService.getById(userService.getByEmail(user.getEmail()).getId()) != null) {
			System.out.println("id a modifier: "+userService.getById(userService.getByEmail(user.getEmail()).getId()).getId());
			if(user.getPassword().equals(user.getConfirmPassword())) {
				System.out.println("mot de passe a modifier: "+user.getPassword());
				User u = userService.getById(userService.getByEmail(user.getEmail()).getId());
				u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				u.setConfirmPassword(bCryptPasswordEncoder.encode(user.getConfirmPassword()));
				userService.editUser(u);
				ra.addFlashAttribute("sucess", "votre mot de passe a bien été modifié");
				return "redirect:/login";
			}else {
				model.addAttribute("incorrect", "la confirmation du mot de passe est incorrecte !");
				model.addAttribute("User", userService.getByEmail(user.getEmail()));
				return "formRecuverPassword";
			}	
		}else {
			ra.addFlashAttribute("notFound", "l'utilisateur avec cette adresse mail n'a pas de compte");
			return "redirect:/passwordrecuver";
		}
	}
}
