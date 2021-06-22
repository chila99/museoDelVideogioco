package it.uniroma3.siw.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.repository.CollezioneRepository;

@Service
public class CollezioneService {
	
	@Autowired
	private CollezioneRepository collezioneRepository;

	@Transactional
	public List<Collezione> tutti() {
		return (List<Collezione>) collezioneRepository.findAll();
	}

	@Transactional
	public Collezione collezionePerId(Long id) {
		return collezioneRepository.findById(id).orElse(null);
	}

	@Transactional
	public Collezione inserisci(Collezione collezione) {
		return this.collezioneRepository.save(collezione);
	}

	public void rimuovi(Long id) {
		collezioneRepository.deleteById(id);
	}

	@Transactional
	public boolean giaEsiste(Collezione c) {
		return this.collezioneRepository.findByNome(c.getNome())!=null;
	}

	@Transactional
	public Collezione collezionePerNome(String nome) {
		return this.collezioneRepository.findByNome(nome);
	}

}
