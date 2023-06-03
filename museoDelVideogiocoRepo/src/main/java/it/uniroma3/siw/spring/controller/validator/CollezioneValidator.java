package it.uniroma3.siw.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.service.CollezioneService;

@Component
public class CollezioneValidator implements Validator{
	
	@Autowired
	private CollezioneService collezioneService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Collezione.class.equals(clazz);
	}

	@Override
	public void validate(Object c, Errors errors) {
		
			if(this.collezioneService.giaEsiste((Collezione)c))
				errors.reject("collezione.duplicato");
	}


}
