package it.uniroma3.siw.spring.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.repository.ArtistaRepository;

@Service
public class ArtistaService {
	
	@Autowired
	private ArtistaRepository artistaRepository;
	
	@Transactional
	public List<Artista> tutti() {
		return (List<Artista>) artistaRepository.findAll();
	}

	@Transactional
	public boolean alreadyExists(Artista a) {
		return artistaRepository.findByNomeAndCognome(a.getNome(), a.getCognome()).size() > 0;
	}

	@Transactional
	public Artista inserisci(Artista artista) {
		 return artistaRepository.save(artista);
	}

	@Transactional
	public void rimuovi(Long id) {
		this.artistaRepository.deleteById(id);
	}

	@Transactional
	public Artista artistaPerId(Long id) {
		return this.artistaRepository.findById(id).orElse(null);
	}

}
