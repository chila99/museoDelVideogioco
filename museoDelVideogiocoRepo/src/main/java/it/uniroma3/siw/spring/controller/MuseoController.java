package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class MuseoController {
	
	@Autowired 
	private CollezioneService collezioneService;
	
	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private OperaService operaService;
	
	@RequestMapping(value="/home", method =RequestMethod.GET)
	public String getHome(Model model) {
		return "index.html";
	}
	
	@RequestMapping( value = "/collezioni", method = RequestMethod.GET)
	public String getCollezioni(Model model) {
		model.addAttribute("collezioni", collezioneService.tutti());
		return "collezioniPageMuseo.html";
	}
	
	@RequestMapping( value = "/artisti", method = RequestMethod.GET)
	public String getArtisti(Model model) {
		model.addAttribute("artisti", artistaService.tutti());
		return "artistiPageMuseo.html";
	}
	
	@RequestMapping( value = "/opere", method = RequestMethod.GET)
	public String getOpere(Model model) {
		model.addAttribute("opere", operaService.tutti());
		return "operePageMuseo.html";
	}
	
	@RequestMapping( value = "/informazioni", method = RequestMethod.GET)
	public String getInformazioni(Model model) {
		return "informazioniPageMuseo.html";
	}

}
