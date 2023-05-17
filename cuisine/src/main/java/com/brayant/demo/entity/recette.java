package com.brayant.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recette")
public class recette {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idRecette;
	
	@Column(name = "nameRecette",nullable = false, unique = true, length = 100)
	private String nameRecette;
	
	@Column(name = "urlImageRecette",nullable = false)
	private String urlImageRecette;
	
	@Column(columnDefinition="text", name = "enteteDescriptionRecette",nullable = false, length = 2000)
	private String enteteDescriptionRecette;
	
	@Column(columnDefinition="text", name = "descriptionRecette",nullable = false)
	private String descriptionRecette;
	
	@Column(name = "datePublication")
	private Date datePublication;
	
	@ManyToOne
	categorie categorie;
	
	@ManyToOne
	private User auteur;
	
	public recette() {}

	public recette(String nameRecette, String urlImageRecette, String enteteDescriptionRecette,
			String descriptionRecette, Date datePublication, com.brayant.demo.entity.categorie categorie, User auteur) {
		super();
		this.nameRecette = nameRecette;
		this.urlImageRecette = urlImageRecette;
		this.enteteDescriptionRecette = enteteDescriptionRecette;
		this.descriptionRecette = descriptionRecette;
		this.datePublication = datePublication;
		this.categorie = categorie;
		this.auteur = auteur;
	}

	public Long getIdRecette() {
		return idRecette;
	}

	public void setIdRecette(Long idRecette) {
		this.idRecette = idRecette;
	}

	public String getNameRecette() {
		return nameRecette;
	}

	public void setNameRecette(String nameRecette) {
		this.nameRecette = nameRecette;
	}

	public String getUrlImageRecette() {
		return urlImageRecette;
	}

	public void setUrlImageRecette(String urlImageRecette) {
		this.urlImageRecette = urlImageRecette;
	}

	public String getEnteteDescriptionRecette() {
		return enteteDescriptionRecette;
	}

	public void setEnteteDescriptionRecette(String enteteDescriptionRecette) {
		this.enteteDescriptionRecette = enteteDescriptionRecette;
	}

	public String getDescriptionRecette() {
		return descriptionRecette;
	}

	public void setDescriptionRecette(String descriptionRecette) {
		this.descriptionRecette = descriptionRecette;
	}

	public Date getDatePublication() {
		return datePublication;
	}

	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}

	public categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(categorie categorie) {
		this.categorie = categorie;
	}

	public User getAuteur() {
		return auteur;
	}

	public void setAuteur(User auteur) {
		this.auteur = auteur;
	}
}
