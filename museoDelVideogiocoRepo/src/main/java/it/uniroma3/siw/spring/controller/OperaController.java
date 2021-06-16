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
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class OperaController {
	
	private static final Logger logger = LogManager.getLogger(CollezioneController.class);
	
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
		return "operaForm";
	}
	
	@RequestMapping(value = "/admin/addOpera", method = RequestMethod.POST)
	public String insertOpera(@ModelAttribute("opera") Opera opera,
						   @ModelAttribute("artista_id") Long artista_id,
						   @ModelAttribute("collezione_id") Long collezione_id,
						   @RequestParam("image") MultipartFile multipartFile,
							Model model, BindingResult bindingResult) throws IOException {
		this.operaValidator.validate(opera, bindingResult);
		if(!bindingResult.hasErrors()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	        opera.setFoto(fileName);
			opera.setArtista(artistaService.artistaPerId(artista_id));
			opera.setCollezioni(new LinkedList<Collezione>());
			opera.getCollezioni().add(this.collezioneService.collezionePerId(collezione_id));
			
			artistaService.artistaPerId(artista_id).getOpereRealizzate().add(opera);
			
			collezioneService.collezionePerId(collezione_id).getOpere().add(opera);
			
			Opera operaSalvata = operaService.inserisci(opera);
			
			String uploadDir = "opera-foto/" + operaSalvata.getId();	 
	        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
			model.addAttribute("opere", operaService.tutti());
			return "operePageMuseo";
		}
		return addOpera(model);
	}
	
	@RequestMapping(value = "/admin/deleteOpera/{id}", method = RequestMethod.GET)
	public RedirectView deleteOpera(@PathVariable("id") Long id,
								Model model) {
		this.operaService.rimuovi(id);
		return new RedirectView("/opere");
	}
	

}
