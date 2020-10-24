package lojaunit.controller;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lojaunit.entities.Faq;
import lojaunit.entities.Produto;
import lojaunit.repository.FaqRepository;

@Controller
@RequestMapping(path="/faq")
public class FaqController {
	@Autowired
	
	private FaqRepository faqRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewFaq(
			@RequestParam Timestamp datahora,
			@RequestParam String texto,
			@RequestParam Produto produto
			) {
		
		Faq faq = new Faq();
		faq.setDatahora(datahora);
		faq.setTexto(texto);
		faq.setProduto(produto);
		faqRepository.save(faq);
		return "Faq";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Faq> getAllFaqs(){
		return faqRepository.findAll();
	}
	
	@GetMapping(path="/find/{id}")
	public @ResponseBody Optional<Faq> getFaqById(@PathVariable("id")Integer id){
		return faqRepository.findById(id);
	}
	
	@DeleteMapping(path="/delete/all")
	public @ResponseBody String deleteAll() {
		faqRepository.deleteAll();
		return "O conteúdo da Tabela Faq foi apagado com Sucesso!";
	}
	
	@DeleteMapping(path="/delete/{id}")
	public @ResponseBody String deleteById(@PathVariable("id")Integer id) {
		if(faqRepository.existsById(id)) {
			faqRepository.deleteById(id);
			return "Faq apagado com sucesso";
		}
		return "Faq não encontrado";
	}
}
