package it.uniroma3.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Collezione;

public interface CollezioneRepository extends CrudRepository<Collezione, Long>{

	public Collezione findByNome(String nome);

}
