package com.brayant.demo.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.brayant.demo.entity.User;
import com.brayant.demo.entity.categorie;
import com.brayant.demo.repository.CategorieRepository;
import com.brayant.demo.service.CategorieService;

@Service
public class CategorieServiceImpl implements CategorieService{

	public CategorieRepository categorieRepository;
	
	public CategorieServiceImpl(CategorieRepository categorieRepository) {
		super();
		this.categorieRepository = categorieRepository;
	}

	@Override
	public List<categorie> getAllCategories() {
		return categorieRepository.findAll();
	}

	@Override
	public List<categorie> getByName(String name) {
		return categorieRepository.findByName(name);
	}

	@Override
	public categorie getById(Long id) {
		return categorieRepository.findById(id).get();
	}

	@Override
	public void delete(Long id) {
			categorieRepository.deleteById(id);	
				
	}

	@Override
	public categorie addCategorie(categorie c) {
		return categorieRepository.save(c);
	}

	@Override
	public categorie editCategorie(categorie c) {
		return categorieRepository.save(c);
	}

	@Override
	public List<categorie> getByAuteur(User auteur) {
		
		return categorieRepository.findByAuteur(auteur);
	}

}
