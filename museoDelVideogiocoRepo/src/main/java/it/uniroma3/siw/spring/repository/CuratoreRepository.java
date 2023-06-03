package it.uniroma3.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Curatore;

public interface CuratoreRepository extends CrudRepository<Curatore, Long>{

	public Curatore findByMatricola(String matricola);

}
