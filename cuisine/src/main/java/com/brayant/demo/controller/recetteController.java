package com.brayant.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
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
public class recetteController {
	
	UserService userService;
	CategorieService categorieService;
	RecetteService recetteService;


	public recetteController(UserService userService, CategorieService categorieService,RecetteService recetteService) {
		super();
		this.userService = userService;
		this.categorieService = categorieService;
		this.recetteService = recetteService;
	}

	@GetMapping("/adminrecettes")
	public String listRecettes(Principal principal, Model model, RedirectAttributes ra) {
		Authentication auth = (Authentication) principal;
		if(auth.isAuthenticated()) {
			User auteur = userService.getByEmail(auth.getName());
			if(auth.getName().equals("brayantmohamed@gmail.com")) {
				model.addAttribute("listRecettes", recetteService.getAllRecettes());
				return "/adminrecettes";	
			}else {
				model.addAttribute("listRecettes", recetteService.getByAuteur(auteur));
				return "/adminrecettes";
			}
		}else {
			ra.addFlashAttribute("error", "erreur de connexion!");
			return "/login";
		}
		
	}
	
	@GetMapping("/adminrecettes/add")
	public String formAddRecettes(Model model) {
		recette recette = new recette();
		List<categorie> listCategorie = categorieService.getAllCategories();
		model.addAttribute("recette", recette);
		model.addAttribute("listCategorie", listCategorie);
		return "/adminformAddRecette";
	}
	
	@RequestMapping(value = "/adminaddRecettes", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String addRecette(@RequestParam("file") MultipartFile  multipartFile,@ModelAttribute("recettes") recette recette,Principal principal,RedirectAttributes ra) throws IOException {
		if(multipartFile.getOriginalFilename().endsWith(".jpg") || multipartFile.getOriginalFilename().endsWith(".jpeg") || multipartFile.getOriginalFilename().endsWith(".png")) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			    if( multipartFile.getSize()<=5000000 && multipartFile.getSize()>0) {
			    List<recette> re = recetteService.getByName(recette.getNameRecette());
				if(re.isEmpty()){
				    File Imagerecette = new File("src/main/resources/static/"+fileName);
				    Imagerecette.createNewFile();
				    FileOutputStream fout = new FileOutputStream( Imagerecette);
			        fout.write( multipartFile.getBytes());
			        fout.close();
					recette recetteToAdd = new recette();
					recetteToAdd.setNameRecette(recette.getNameRecette());
					recetteToAdd.setEnteteDescriptionRecette(recette.getEnteteDescriptionRecette());
					recetteToAdd.setCategorie(recette.getCategorie());
					recetteToAdd.setDatePublication(new Date());
					recetteToAdd.setDescriptionRecette(recette.getDescriptionRecette());
					recetteToAdd.setUrlImageRecette(fileName);
			        Authentication auth = (Authentication) principal;
			        if(auth.isAuthenticated()) {
						User auteur = userService.getByEmail(auth.getName());
						recetteToAdd.setAuteur(auteur);
					}
			        recetteService.addRecette(recetteToAdd);
			        ra.addFlashAttribute("sucess", "la recette a été crée avec success !");
			        return "redirect:/adminrecettes";
				}else {
					ra.addFlashAttribute("error", "cette recette a déja été ajoutée par un autre utilisateur!");
					return "redirect:/adminrecettes";
				}

			}
			else {
				ra.addFlashAttribute("error", "veillez verifier la taille de votre fichier !");
				return "redirect:/adminrecettes";
			}
		}
		else {
			ra.addFlashAttribute("error", "veillez vous assurer que votre fichier est une image !");
			return "redirect:/adminrecettes";
		}
	}
	
	@GetMapping("/adminrecette/update")
	public String formUpdateRecette(@RequestParam("id") Long id, Model model) {
		List<categorie> listCategorie = categorieService.getAllCategories();
		model.addAttribute("listCategorie", listCategorie);
		model.addAttribute("recette",recetteService.getById(id));
		return "/adminformUpdateRecette";
	}
	
	@PostMapping("/adminupdateRecette")
	public String updateRecette( @RequestParam("id") Long id, @ModelAttribute("recette") recette recette, RedirectAttributes ra) {
		recette rec = new recette();
		rec = recetteService.getById(id);
		rec.setNameRecette(recette.getNameRecette());
		rec.setEnteteDescriptionRecette(recette.getEnteteDescriptionRecette());
		rec.setDescriptionRecette(recette.getDescriptionRecette());
		//rec.setUrlImageRecette(recette.getUrlImageRecette());
		rec.setCategorie(recette.getCategorie());
		
		recetteService.editRecette(rec);
		ra.addFlashAttribute("sucess", "la recette a été modifiée avec success");
		return "redirect:/adminrecettes";
	}
	
	@GetMapping("/adminrecette/delete")
	public String deleteRecette(@RequestParam("id") Long id, RedirectAttributes ra) {
	
		recetteService.deleteRecette(id);
		ra.addFlashAttribute("sucess", "la recette a été suprimée avec success");
		return "redirect:/adminrecettes";
	}
	
}
