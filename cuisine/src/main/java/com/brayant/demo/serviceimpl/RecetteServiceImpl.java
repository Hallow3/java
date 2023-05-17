package com.brayant.demo.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.brayant.demo.entity.User;
import com.brayant.demo.entity.categorie;
import com.brayant.demo.entity.recette;
import com.brayant.demo.repository.RecetteRepository;
import com.brayant.demo.repository.specification.RecetteSpecification;
import com.brayant.demo.service.RecetteService;

@Service
public class RecetteServiceImpl implements RecetteService{

public RecetteRepository recetteRepository;
	
	public RecetteServiceImpl(RecetteRepository recetteRepository) {
		super();
		this.recetteRepository = recetteRepository;
	}

	@Override
	public List<recette> getAllRecettes() {
		return recetteRepository.findAll();
	}

	@Override
	public List<recette> getByName(String nameRecette) {
		return recetteRepository.findAll(RecetteSpecification.seachRecette(nameRecette));
	}

	@Override
	public recette getById(Long id) {
		return recetteRepository.findById(id).get();
	}

	/*@Override
	public List<recette> getByCategorie(categorie categorie) {
		List<recette> liste = recetteRepository.findAll();
		List<recette> listeVide = new ArrayList<>();
		for(recette rec: liste) {
			if(rec.getCategorie()==categorie) {
				listeVide.add(rec);
			}
		}
		return listeVide;
	}
*/
	@Override
	public List<recette> getByCategorie(categorie cat) {
		return recetteRepository.findByCategorie(cat);
	}
	
	@Override
	public void deleteRecette(Long id) {
		recetteRepository.deleteById(id);	
	}
	
	@Override
	public recette addRecette(recette r) {
		return recetteRepository.save(r);
	}
	
	@Override
	public recette editRecette(recette r) {
		return recetteRepository.save(r);
	}

	@Override
	public List<recette> getByAuteur(User auteur) {
		
		return recetteRepository.findByAuteur(auteur);
	}
}
