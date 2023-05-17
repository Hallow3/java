package com.brayant.demo.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.brayant.demo.entity.User;
import com.brayant.demo.entity.categorie;

public interface CategorieRepository extends JpaRepository<categorie, Long>{

	List<categorie>  findAll(Specification<categorie> seachCategorie);
	
	List<categorie> findByName(String name);
	
	List<categorie> findByAuteur(User auteur);
}
