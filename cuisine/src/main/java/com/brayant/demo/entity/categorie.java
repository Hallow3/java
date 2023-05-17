package com.brayant.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categorie")
public class categorie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", nullable = false, unique = true, length = 100)
	private String name;
	
	@Column(name = "urlImage",nullable = false)
	private String urlImage;
	
	@Column(name = "description",nullable = false)
	private String description;
	
	@ManyToOne
	private User auteur;
	
	@OneToMany(mappedBy = "categorie")
	List<recette> recettes;
	
	public categorie() {}

	public categorie(Long id, String name, String urlImage, String description, User auteur, List<recette> recettes) {
		super();
		this.id = id;
		this.name = name;
		this.urlImage = urlImage;
		this.description = description;
		this.auteur = auteur;
		this.recettes = recettes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getAuteur() {
		return auteur;
	}

	public void setAuteur(User auteur) {
		this.auteur = auteur;
	}

	public List<recette> getRecettes() {
		return recettes;
	}

	public void setRecettes(List<recette> recettes) {
		this.recettes = recettes;
	}

	@Override
	public String toString() {
		return "categorie [name=" + name + ", urlImage=" + urlImage + ", description=" + description + ", auteur="
				+ auteur + ", recettes=" + recettes + "]";
	}
}
