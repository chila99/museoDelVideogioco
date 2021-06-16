package it.uniroma3.siw.spring.controller.validator;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.OperaService;

@Component
public class OperaValidator implements Validator{

	@Autowired
	private OperaService operaService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Opera.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		
		
		
		Opera opera = (Opera) o;
		
		if(opera.getTitolo().isEmpty())
			errors.reject("titolo.nullo");
		
		if(opera.getDescrizione().isEmpty())
			errors.reject("descrizione.nullo");
		
		if(opera.getAnnoRealizzazione().equals(null))
			errors.reject("anno.nullo");
		
		if(opera.getAnnoRealizzazione().compareTo(Long.valueOf(LocalDate.now().getYear())) > 0)
			errors.reject("anno.futuro");
		
		if(this.operaService.giaEsiste((Opera)o))
			errors.reject("opera.duplicato");
	}

}
