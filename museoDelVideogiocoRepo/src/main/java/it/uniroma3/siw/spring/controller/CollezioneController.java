package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import it.uniroma3.siw.spring.controller.validator.CollezioneValidator;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Curatore;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.CuratoreService;

@Controller
public class CollezioneController {
	
	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private CollezioneValidator collezioneValidator;

	@Autowired
	private CuratoreService curatoreService;
	
	@RequestMapping(value ="/collezioni/{id}", method = RequestMethod.GET)
	public String getArtista(@PathVariable("id") Long id, Model model) {
		model.addAttribute("collezione", collezioneService.collezionePerId(id));
		model.addAttribute("curatore", collezioneService.collezionePerId(id).getGestore());
		model.addAttribute("opere", collezioneService.collezionePerId(id).getOpere());
		return "collezionePageMuseo";
	}
	
	@RequestMapping(value="/admin/collezione", method = RequestMethod.GET)
	public String addCollezione(Model model) {
		model.addAttribute("curatori", this.curatoreService.tutti());
		return "curatori.html";
	}
	
	@RequestMapping(value="/admin/collezione/{id}", method = RequestMethod.GET)
	public String addCollezioneAlCuratore(@PathVariable("id") Long id,
											Model model) {
		Curatore curatore = this.curatoreService.cercaPerId(id);
		model.addAttribute("matricola", curatore.getMatricola());
		model.addAttribute("collezione", new Collezione());
		return "collezioneForm.html";
	}
	
	@RequestMapping(value="/admin/addCollezione", method = RequestMethod.POST)
	public String addCollezione(@ModelAttribute("collezione") Collezione collezione,
								@ModelAttribute("matricola") String matricola,
								Model model, BindingResult bindingResult) {
		this.collezioneValidator.validate(collezione, bindingResult);
		if (!bindingResult.hasErrors()) {
			collezione.setGestore(curatoreService.cercaPerMatricola(matricola));
        	this.collezioneService.inserisci(collezione);
            model.addAttribute("collezioni", this.collezioneService.tutti());
            return "collezioniPageMuseo";
        }
		return "collezioneForm";
	}
	
	@RequestMapping(value="/admin/removeCollezione/{id}", method = RequestMethod.GET)
	public RedirectView removeCollezione(@PathVariable("id") Long id ,Model model) {
		this.collezioneService.rimuovi(id);
		return new RedirectView("/collezioni");
	}

}
