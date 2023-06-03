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

import it.uniroma3.siw.spring.controller.validator.ArtistaValidator;
import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.service.ArtistaService;

@Controller
public class ArtistaController {
	
	@Autowired
	private ArtistaValidator artistaValidator;

	@Autowired
	private ArtistaService artistaService;
	
	@RequestMapping(value="/artista/{id}", method = RequestMethod.GET)
	public String getArtista(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artista", this.artistaService.artistaPerId(id));
		model.addAttribute("opere", this.artistaService.artistaPerId(id).getOpereRealizzate());
		return "artistaPageMuseo";
	}
	
	@RequestMapping(value="/admin/artista", method = RequestMethod.GET)
	public String addArtista(Model model) {
		model.addAttribute("artista", new Artista());
		return "artistaForm.html";
	}
	
	@RequestMapping(value="/admin/addArtista", method = RequestMethod.POST)
	public String addArtista(@ModelAttribute("artista") Artista artista,
								Model model, BindingResult bindingResult) {
		this.artistaValidator.validate(artista, bindingResult);
		if (!bindingResult.hasErrors()) {
        	this.artistaService.inserisci(artista);
            model.addAttribute("artisti", this.artistaService.tutti());
            return "artistiPageMuseo";
        }
		return "artistaForm";
	}
	
	@RequestMapping(value="/admin/removeArtista/{id}", method = RequestMethod.GET)
	public RedirectView removeArtista(@PathVariable("id") Long id ,Model model) {
		this.artistaService.rimuovi(id);
		return new RedirectView("/artisti");
	}

}
