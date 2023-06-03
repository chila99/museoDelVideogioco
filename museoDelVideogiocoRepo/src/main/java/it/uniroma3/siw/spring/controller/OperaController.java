package it.uniroma3.siw.spring.controller;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import it.uniroma3.siw.spring.controller.validator.OperaValidator;
import it.uniroma3.siw.spring.middleware.FileUploadUtil;
import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class OperaController {
	
	private static final Logger logger =  LogManager.getLogger(OperaController.class);
	
	@Autowired
	private OperaService operaService;
	
	@Autowired
	private OperaValidator operaValidator;
	
	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private CollezioneService collezioneService;
	
	@RequestMapping(value = "/opera/{id}", method = RequestMethod.GET)
	public String getOpera(@PathVariable("id") Long id, Model model) {
		Opera opera = operaService.operaPerId(id);
		model.addAttribute("opera", opera);
		model.addAttribute("artista", opera.getArtista());
		return "operaPageMuseo.html";
	}
	
	@RequestMapping(value = "/admin/opera", method = RequestMethod.GET)
	public String addOpera(Model model) {
		model.addAttribute("opera", new Opera());
		model.addAttribute("artisti", artistaService.tutti());
		model.addAttribute("collezioni", collezioneService.tutti());
		model.addAttribute("autore", new Artista());
		model.addAttribute("collezionee", new Collezione());
		return "operaForm";
	}
	
	@RequestMapping(value = "/admin/addOpera", method = RequestMethod.POST)
	public String insertOpera(@ModelAttribute("autore") Artista artista,
						      @ModelAttribute("collezionee") Collezione collezione,
						      @ModelAttribute("opera") Opera opera,
						      @RequestParam("image") MultipartFile multipartFile,
							  Model model, BindingResult bindingResult) throws IOException {
		logger.debug(collezione);
		this.operaValidator.validate(opera, bindingResult);
		if(!bindingResult.hasErrors()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	        opera.setFoto(fileName);
			opera.setArtista(artistaService.artistaPerId(artista.getId()));
			opera.setCollezioni(new LinkedList<Collezione>());
			opera.getCollezioni().add(collezioneService.collezionePerNome(collezione.getNome()));
			
			Opera operaSalvata = operaService.inserisci(opera);
			
			String uploadDir = "opera-foto/" + operaSalvata.getId();	 
	        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
			model.addAttribute("opere", operaService.tutti());
			return "operePageMuseo";
		}
		model.addAttribute("artisti", artistaService.tutti());
		model.addAttribute("collezioni", collezioneService.tutti());
		return "operaForm";
	}
	
	@RequestMapping(value = "/admin/deleteOpera/{id}", method = RequestMethod.GET)
	public RedirectView deleteOpera(@PathVariable("id") Long id,
								Model model) {
		this.operaService.rimuovi(id);
		return new RedirectView("/opere");
	}
	
	@RequestMapping(value = "/admin/modificaOpera/{id}", method = RequestMethod.GET)
	public String modificaOpera(@PathVariable("id") Long id, Model model) {
		model.addAttribute("opera", this.operaService.operaPerId(id));
		model.addAttribute("collezioni", this.collezioneService.tutti());
		model.addAttribute("artisti", this.artistaService.tutti());	
		model.addAttribute("collezionee", new Collezione());
		model.addAttribute("autore", new Artista());
		return "operaForm";
	}
	
	@RequestMapping(value = "/admin/modificaOpera", method = RequestMethod.POST)
	public String modificaOpera(@ModelAttribute("opera") Opera opera,
								BindingResult bindingResult, @ModelAttribute("autore") Artista artista,
								@ModelAttribute("collezionee") Collezione collezione, Model model) {
		//this.operaValidator.validate(opera, bindingResult);
		if(!bindingResult.hasErrors()) {
			opera = this.operaService.operaPerId(opera.getId());
			opera.setArtista(this.artistaService.artistaPerId(artista.getId()));
			opera.getCollezioni().add(this.collezioneService.collezionePerNome(collezione.getNome()));
			this.operaService.inserisci(opera);
			return "operePageMuseo";
			}
		model.addAttribute("artisti", artistaService.tutti());
		model.addAttribute("collezioni", collezioneService.tutti());
		model.addAttribute("opera", opera);
		return "operaForm";
	}
}
