package com.brayant.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.brayant.demo.entity.User;
import com.brayant.demo.entity.categorie;
import com.brayant.demo.entity.recette;
import com.brayant.demo.service.CategorieService;
import com.brayant.demo.service.RecetteService;
import com.brayant.demo.service.UserService;


@Controller
public class categorieController {
	UserService userService;
	CategorieService categorieService;
	RecetteService recetteService;

	public categorieController(UserService userService, CategorieService categorieService,
			RecetteService recetteService) {
		super();
		this.userService = userService;
		this.categorieService = categorieService;
		this.recetteService = recetteService;
	}

	@GetMapping("/admincategories")
	public String listCategorie(Model model, Principal principal, RedirectAttributes ra) {
		Authentication auth = (Authentication) principal;
		if(auth.isAuthenticated()) {
			User auteur = userService.getByEmail(auth.getName());
			if(auth.getName().equals("brayantmohamed@gmail.com")) {
				model.addAttribute("listCategorie", categorieService.getAllCategories());
				return "/admincategories";	
			}else {
				model.addAttribute("listCategorie", categorieService.getByAuteur(auteur));
				return "/admincategories";
			}
		}else {
			ra.addFlashAttribute("error", "erreur de connexion!");
			return "/login";
		}
		
	}
	
	@GetMapping("/admincategorie/add")
	public String formAddCategorie(Model model) {
		categorie categorie = new categorie();
		model.addAttribute("categorie", categorie);
		return "/adminformAddCategorie";
	}
	
	@RequestMapping(value = "/adminaddCategories", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String addCategorie(@RequestParam("file") MultipartFile  multipartFile, @ModelAttribute("categorie") categorie cat,Principal principal, RedirectAttributes ra) throws IOException {
		  if(multipartFile.getOriginalFilename().endsWith(".jpg") || multipartFile.getOriginalFilename().endsWith(".jpeg") || multipartFile.getOriginalFilename().endsWith(".png")) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			    if( multipartFile.getSize()<=5000000 && multipartFile.getSize()>0) {
			    List<categorie> ca = categorieService.getByName(cat.getName());
			    if(ca.isEmpty()) {
				    File ImageCategorie = new File("src/main/resources/static/"+fileName);
				    ImageCategorie.createNewFile();
				    FileOutputStream fout = new FileOutputStream( ImageCategorie);
			        fout.write( multipartFile.getBytes());
			        fout.close();
					categorie categorieToAdd = new categorie();
					categorieToAdd.setName(cat.getName());
					categorieToAdd.setDescription(cat.getDescription());
					categorieToAdd.setUrlImage(fileName);
					Authentication auth = (Authentication) principal;
					if(auth.isAuthenticated()) {
						User auteur = userService.getByEmail(auth.getName());
						categorieToAdd.setAuteur(auteur);
					}
			        categorieService.addCategorie(categorieToAdd);
			        ra.addFlashAttribute("sucess", "la categorie a été crée avec sucess !");
			        return "redirect:/admincategories";
			    }else {
			    	
			    	ra.addFlashAttribute("error", "cette catégorie a été ajoutée par un autre utilisateur mais vous pouvez l'utiliser");
					return "redirect:/admincategories";
			    }
			    	
			}
			else {
				ra.addFlashAttribute("error", "veillez verifier que la taille de votre image soit inferieur a 5 Mo");
				return "redirect:/admincategories";
			}
		}
		else {
			ra.addFlashAttribute("error", "votre type d'image n'est pas pris en compte par le système!");
			return "redirect:/admincategories";
		}
	}
	
	@GetMapping("/admincategorie/update")
	public String formUpdateCategorie(@RequestParam("id") Long id, Model model) {
		categorie cat = categorieService.getById(id);
		model.addAttribute("categorie",cat);
		return "/adminformUpdateCategorie";
	}
	
	@PostMapping("/adminupdateCategorie")
	public String updateCategorie(@RequestParam("id") Long id, @ModelAttribute("categorie") categorie categorie,RedirectAttributes ra) {
		categorie cat = new categorie();
		cat = categorieService.getById(id);
		cat.setName(categorie.getName());
		cat.setDescription(categorie.getDescription());
		//cat.setUrlImage(categorie.getUrlImage());
		categorieService.editCategorie(cat);
		
		ra.addFlashAttribute("sucess", "la catégorie a été modifiée avec sucess");
		return "redirect:/admincategories";
	}
	
	@GetMapping("/admincategorie/delete")
	public String deleteCategorie(@RequestParam("id") Long id, RedirectAttributes ra) {
		List<recette> rec = recetteService.getByCategorie(categorieService.getById(id));
		if(!rec.isEmpty()) {
			ra.addFlashAttribute("error", "la catégorie à supprimer possède des recettes veillez d'abord supprimer ces recettes !");
			return "redirect:/admincategories";
		}else {
			categorieService.delete(id);
			ra.addFlashAttribute("sucess", "la catégorie a été supprimée avec sucess");
			return "redirect:/admincategories";
		}
	}
}
