package com.brayant.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.brayant.demo.entity.User;
import com.brayant.demo.entity.categorie;
import com.brayant.demo.entity.recette;

@Service
public interface RecetteService {

	List<recette> getAllRecettes();
	
	List<recette> getByName(String nameRecette);
	
	recette getById(Long id);
	
	void deleteRecette(Long id);
	
	recette editRecette(recette r);
	
	recette addRecette(recette r);

	List<recette> getByCategorie(categorie cat);
	
	List<recette> getByAuteur(User auteur);
}
