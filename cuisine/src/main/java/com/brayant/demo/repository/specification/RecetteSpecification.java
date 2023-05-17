package com.brayant.demo.repository.specification;
import org.springframework.data.jpa.domain.Specification;

import com.brayant.demo.entity.recette;
public class RecetteSpecification {

	public static Specification<recette> seachRecette(String nameRecette){
		return (Specification<recette>)(root,criteriaQuery,criteriaBuilder)->criteriaBuilder.like(root.get("nameRecette"), "%".concat(nameRecette).concat("%"));
	}
}
