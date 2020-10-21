package lojaunit.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lojaunit.entities.Faq;
import lojaunit.repository.FaqRepository;

@Controller
@RequestMapping(path="/faq")
public class FaqController {
	@Autowired
	
	private FaqRepository faqRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewFaq(
			@RequestParam Integer id,
			@RequestParam Date datahora,
			@RequestParam String texto
			) {
		
		Faq faq = new Faq();
		faq.setId(id);
		faq.setDatahora(datahora);
		faq.setTexto(texto);
		faqRepository.save(faq);
		return "Faq";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Faq> getAllFaqs(){
		return faqRepository.findAll();
	}
}
