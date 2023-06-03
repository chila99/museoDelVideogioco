package it.uniroma3.siw.spring.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Curatore;
import it.uniroma3.siw.spring.repository.CuratoreRepository;

@Service
public class CuratoreService {

	@Autowired
	private CuratoreRepository curatoreRepository;

	@Transactional
	public Curatore cercaPerMatricola( String matricola) {
		return this.curatoreRepository.findByMatricola(matricola);
	}
	
	@Transactional
	public Curatore cercaPerId(Long id) {
		return this.curatoreRepository.findById(id).orElse(null);
	}

	@Transactional
	public List<Curatore> tutti() {
		return (List<Curatore>) this.curatoreRepository.findAll();
	}
	
}
