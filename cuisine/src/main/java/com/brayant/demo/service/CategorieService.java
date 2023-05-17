package com.brayant.demo.service;

import java.util.List;

import com.brayant.demo.entity.User;
import com.brayant.demo.entity.categorie;
import com.brayant.demo.exception.NoPermissionException;

public interface CategorieService {
	
	List<categorie> getAllCategories();
	
	List<categorie> getByName(String name);
	
	categorie getById(Long id);
	
	void delete(Long id) throws NoPermissionException;
	
	categorie addCategorie(categorie c);
	
	categorie editCategorie(categorie c);
	
	List<categorie> getByAuteur(User auteur);
 }
