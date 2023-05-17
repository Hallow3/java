package com.steph.authentification.controller;


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

import com.steph.authentification.api.ApiService;
import com.steph.authentification.entity.User;
import com.steph.authentification.service.UserService;


@Controller
public class Homepage {
	//class pour crypter le mot de passe
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	//declaration des variables pour le process
	UserService userService;
	
	//declaration de la variable pour les mails
	ApiService api = new ApiService();
					
	//variable pour le nombre aleatoire 
	int nombre1 = 0;
	
	public Homepage(UserService userService) {
		super();
		
		this.userService = userService;
	}
	
	//mes message pour les mails
	public String messageToSend(int code) {
		return "Bonjour monsieur/madame nous vous envoyons ce mail"
				+ " parceque vous venez de vous inscrire à l'application"
				+ " BOCOBI237.</br>"
				+ " afin de poursuivre votre inscription vous devez soumettre le code <h3> "
				+ code +"</h3> dans le site pour procéder à la vérification de votre adresse</br>"
				+ " merci pour votre compréhension n'hésitez pas a soumettre vos questions"
				+ " au +237656080350 à bientot.";
	}
	public String messageToSend2(int code) {
		return "Bonjour monsieur/madame nous vous envoyons ce mail"
				+ " parceque vous venez de faire une demande de modification de mot de passe pour "
				+ " BOCOBI237.</br>"
				+ " afin de poursuivre votre opération, vous devez soumettre le code <h3> "
				+ code +"</h3> dans le site pour procéder à la vérification de votre adresse</br>"
				+ " merci pour votre compréhension n'hésitez pas a soumettre vos questions"
				+ " au +237656080350 à bientot.";
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

	
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
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

/*
 * 
 * 
 * //Définir le contrôleur pour la récupération du mot de passe
 * 
 * @RestController
 * 
 * @RequestMapping("/api") public class PasswordRecoveryController {
 * 
 * private final UserService userService; private final ApiService api; private
 * final BCryptPasswordEncoder bCryptPasswordEncoder; private int nombre1 = 0;
 * 
 * public PasswordRecoveryController(UserService userService, ApiService api,
 * BCryptPasswordEncoder bCryptPasswordEncoder) { this.userService =
 * userService; this.api = api; this.bCryptPasswordEncoder =
 * bCryptPasswordEncoder; }
 * 
 * // Endpoint pour envoyer un e-mail de récupération de mot de passe
 * 
 * @PostMapping("/password-recovery") public ResponseEntity<String>
 * sendPasswordRecoveryEmail(@RequestBody User user, Model model,
 * RedirectAttributes ra) throws AddressException, MessagingException{
 * System.out.println("E-mail de récupération de mot de passe : "+user.getEmail(
 * )); // Vérifier si l'utilisateur existe User u1 =
 * userService.getByEmail(user.getEmail()); if(u1 != null) {
 * System.out.println("E-mail existant : "+user.getEmail()); // Générer un
 * nombre aléatoire et envoyer un e-mail avec ce nombre nombre1 = (int) new
 * Random().nextInt(1000000 - 100000 + 1) + 100000;
 * System.out.println("Nombre généré : "+nombre1); int nombre = nombre1+1024;
 * api.sendmail(user.getEmail(), messageToSend2(nombre1).indent(1),
 * "Récupération Du Mot De Passe");
 * System.out.println("Code envoyé : "+nombre1); model.addAttribute("User",
 * user); model.addAttribute("nombre", nombre); return
 * ResponseEntity.ok("E-mail envoyé pour la récupération du mot de passe"); }
 * else { ra.addFlashAttribute("notFound",
 * "veillez vous assurer que l'e-mail est valide"); return
 * ResponseEntity.badRequest().body("Utilisateur introuvable"); } }
 * 
 * // Endpoint pour vérifier le code de récupération de mot de passe et renvoyer
 * le formulaire de modification de mot de passe
 * 
 * @PostMapping("/verify-password-recovery-code") public ResponseEntity<String>
 * verifyPasswordRecoveryCode(@ModelAttribute("User") User
 * user, @RequestParam("nombre") String nombre, @RequestParam("number") String
 * number, Model model, RedirectAttributes ra) { int nombre1 =
 * Integer.parseInt(nombre.replace(",", "")); int number1 =
 * Integer.parseInt(number.replace(",", "")); System.out.println("Nombre : " +
 * nombre1 + " Numéro : " + number1); if(user.getEmail() != null) {
 * if(userService.getByEmail(user.getEmail()) != null) {
 * System.out.println("Vérification des nombres"); if(number1 == nombre1 - 1024)
 * { model.addAttribute("User", userService.getByEmail(user.getEmail())); return
 * ResponseEntity.ok("Code de vérification correct"); } else {
 * model.addAttribute("nombre", nombre.replace(",", ""));
 * model.addAttribute("code", "Le code de vérification est incorrect");
 * model.addAttribute("User", userService.getByEmail(user.getEmail())); return
 * ResponseEntity.badRequest().body("Code de vérification incorrect"); } } else
 * { ra.addFlashAttribute("refuse", "L'utilisateur est introuvable"); return
 * ResponseEntity.badRequest().body("Utilisateur introuvable"); } } else {
 * ra.addFlashAttribute("refuse", "L'utilisateur est introuvable"); return
 * ResponseEntity.badRequest().body("Utilisateur introuvable"); } }
 * 
 * // Endpoint pour modifier le mot de passe
 * 
 * @PostMapping("/change-password") public ResponseEntity<String>
 * changePassword(@ModelAttribute("User") User user, Model model,
 * RedirectAttributes ra) {
 * if(userService.getById(userService.getByEmail(user.getEmail()).getId()) !=
 * null) {
 * System.out.println("ID de l'utilisateur à modifier : "+userService.getById(
 * userService.getByEmail(user.getEmail()).getId()).getId());
 * if(user.getPassword().equals(user.getConfirmPassword())) {
 * System.out.println("Mot de passe à modifier : "+user.getPassword()); User u =
 * userService.getById(userService.getByEmail(user.getEmail()).getId());
 * u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
 * u.setConfirmPassword(bCryptPasswordEncoder.encode(user.getConfirmPassword()))
 * ; userService.saveOrUpdate(u); return
 * ResponseEntity.ok("Mot de passe modifié avec succès"); } else {
 * ra.addFlashAttribute("dontMatch", "Les mots de passe ne correspondent pas");
 * return
 * ResponseEntity.badRequest().body("Les mots de passe ne correspondent pas"); }
 * } else { ra.addFlashAttribute("refuse", "L'utilisateur est introuvable");
 * return ResponseEntity.badRequest().body("Utilisateur introuvable"); } } }
 */

/*
 * 
 * @RestController
 * 
 * @RequestMapping("/api") public class HomepageController {
 * 
 * private final UserService userService; private final ApiService api; private
 * final BCryptPasswordEncoder bCryptPasswordEncoder; private int nombre1 = 0;
 * 
 * public HomepageController(UserService userService, ApiService api,
 * BCryptPasswordEncoder bCryptPasswordEncoder) { this.userService =
 * userService; this.api = api; this.bCryptPasswordEncoder =
 * bCryptPasswordEncoder; }
 * 
 * @PostMapping("/confirm-mail") public ResponseEntity<String>
 * confirmMail(@RequestBody User user) throws AddressException,
 * MessagingException { if(user.getEmail() != null) { nombre1 = (int) new
 * Random().nextInt(1000000 - 100000 + 1) + 100000;
 * System.out.println("nombre généré: "+nombre1); int nombre = nombre1+1024;
 * api.sendmail(user.getEmail(), messageToSend(nombre1).indent(1),
 * "Inscription"); return ResponseEntity.ok("Message envoyé avec succès"); }
 * else { return
 * ResponseEntity.badRequest().body("L'adresse e-mail est invalide"); } }
 * 
 
 * 
 * 
 *8
 * import java.nio.ByteBuffer; import java.security.MessageDigest; import
 * java.security.NoSuchAlgorithmException; import java.security.SecureRandom;
 * 
 * // ...
 * 
 * @PostMapping("/verify") public String verifyEmail(@ModelAttribute("User")
 * User user, Model model, RedirectAttributes ra) throws AddressException,
 * MessagingException { System.out.println("mail: " + user.getEmail()); User u1
 * = userService.getByEmail(user.getEmail()); if (u1 != null) {
 * System.out.println("mail existant: " + user.getEmail()); SecureRandom random
 * = new SecureRandom(); byte[] bytes = new byte[4]; random.nextBytes(bytes);
 * int nombre1 = Math.abs(ByteBuffer.wrap(bytes).getInt()) % 1000000;
 * System.out.println("nombre généré: " + nombre1); int nombre = nombre1+1024;
 * api.sendmail(user.getEmail(), messageToSend2(nombre1).indent(1),
 * "Récupération Du Mot De Passe"); System.out.println("code envoyé: " +
 * nombre1); model.addAttribute("User", user); model.addAttribute("nombre",
 * nombre); return "recover"; } else { ra.addFlashAttribute("notFound",
 * "veillez vous assurer que l'e-mail est valide"); return
 * "redirect:/passwordrecuver"; } }
 * 
 * @PostMapping("/formpasswordrecuver") public String formrecuverPassword(User
 * user, @RequestParam("nombre") String nombre, @RequestParam("number") String
 * number,Model model, RedirectAttributes ra) throws NoSuchAlgorithmException {
 * int nombre1 = Integer.parseInt(nombre.replace(",", "")); int number1 =
 * Integer.parseInt(number.replace(",", "")); System.out.println("nombre: " +
 * nombre1 + " number: " + number1); if (user.getEmail() != null) { if
 * (userService.getByEmail(user.getEmail()) != null) {
 * System.out.println("verification des nombres"); MessageDigest md =
 * MessageDigest.getInstance("SHA-256"); byte[] hash =
 * md.digest(Integer.toString(nombre1).getBytes()); String hashString = new
 * String(hash); if (hashString.equals(number)) { model.addAttribute("User",
 * userService.getByEmail(user.getEmail())); return "formRecuverPassword"; }
 * else { model.addAttribute("nombre", nombre.replace(",", ""));
 * model.addAttribute("code", "le code de vérification est incorrect ");
 * model.addAttribute("User", userService.getByEmail(user.getEmail())); return
 * "recover"; } } else { ra.addFlashAttribute("refuse",
 * "l'utilisateur est introuvable"); return "redirect:/login"; } } else {
 * ra.addFlashAttribute("refuse", "l'utilisateur est introuvable"); return
 * "redirect:/login"; } }
 * 
 * @PostMapping("/passwordrecuver") public String
 * passwordRecuver(@ModelAttribute("User") User user, Model model,
 * RedirectAttributes ra) throws NoSuchAlgorithmException { if
 * (userService.getById(userService.getByEmail(user.getEmail()).getId()) !=
 * null) { System.out.println("id a modifier: " +
 * userService.getById(userService.getByEmail(user.getEmail()).getId()).getId())
 * ; if (user.getPassword().equals(user.getConfirmPassword())) {
 * System.out.println("mot de passe a modifier: " + user.getPassword()); User u
 * = userService.getById(userService.getByEmail(user.getEmail()).getId());
 * MessageDigest md = MessageDigest.getInstance("SHA-256"); byte[] hash =
 * md.digest(user.getPassword().getBytes()); String hashString = new
 * String(hash); u.setPassword(bCryptPasswordEncoder.encode(hashString));
 * u.setConfirmPassword(bCryptPasswordEncoder.encode(hashString));
 * userService.editUser(u); ra.addFlashAttribute("sucess",
 * "votre mot de passe a bien été modifié"); return "redirect:/login"; } else {
 * model.addAttribute("incorrect",
 * "la confirmation du mot de passe est incorrecte !");
 * model.addAttribute("User", userService.getByEmail(user.getEmail())); return
 * "formRecuverPassword"; } } else { ra.addFlashAttribute("notFound",
 * "l'utilisateur avec cette adresse mail n'a pas de compte"); return
 * "redirect:/passwordrecuver"; } }
 */