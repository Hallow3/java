package com.brayant.demo.repository.specification;
import org.springframework.data.jpa.domain.Specification;

import com.brayant.demo.entity.categorie;
public class CategorieSpecification {

	public static Specification<categorie> seachCategorie(String name){
		return (Specification<categorie>)(root,criteriaQuery,criteriaBuilder)->criteriaBuilder.like(root.get(name), "%".concat(name).concat("%"));
	}
}
