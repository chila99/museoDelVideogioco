package it.uniroma3.siw.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.repository.OperaRepository;

@Service
public class OperaService {

	@Autowired
	private OperaRepository operaRepository;
	
	@Transactional
	public List<Opera> tutti() {
		return (List<Opera>) operaRepository.findAll();
	}

	@Transactional
	public Opera inserisci(Opera opera) {
		return operaRepository.save(opera);
	}

	@Transactional
	public void rimuovi(Long id) {
		operaRepository.deleteById(id);
	}

	@Transactional
	public Opera operaPerId(Long id) {
		return this.operaRepository.findById(id).orElse(null);
	}

	@Transactional
	public boolean giaEsiste(Opera o) {
		return this.operaRepository.findByTitolo(o.getTitolo()) != null;
	}

}
