package it.uniroma3.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Opera;

public interface OperaRepository extends CrudRepository<Opera, Long>{

	public Opera findByTitolo(String titolo);

}
