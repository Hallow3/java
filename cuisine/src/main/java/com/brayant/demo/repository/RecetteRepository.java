package com.brayant.demo.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.brayant.demo.entity.User;
import com.brayant.demo.entity.categorie;
import com.brayant.demo.entity.recette;

public interface RecetteRepository extends JpaRepository<recette, Long>{

	List<recette> findAll(Specification<recette> seachRecette);
	
	List<recette> findByCategorie(categorie cat);
	
	List<recette> findByAuteur(User auteur);
	
}
