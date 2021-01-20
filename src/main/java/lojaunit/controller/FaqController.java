package lojaunit.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Path.Node;
import javax.validation.UnexpectedTypeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import lojaunit.entities.Faq;
import lojaunit.entities.Marca;
import lojaunit.entities.Produto;
import lojaunit.repository.FaqRepository;
import lojaunit.repository.ProdutoRepository;

@Controller
@RequestMapping(path="/faq")
public class FaqController {
	@Autowired
	private FaqRepository faqRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@RequestMapping(value="/add", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<T> addNewFaq(@Valid
			@RequestBody ObjectNode faq
			/*@RequestParam Timestamp datahora,
			@RequestParam String texto,
			@RequestParam Integer idProduto*/
			) {
		//https://medium.com/@andylke/rest-controller-configure-date-time-format-in-json-response-201e97aa74b0
		Faq faq2 = new Faq();
		//faq2.setDatahora(faq.get("datahora"));
		faq2.setDatahora(Timestamp.valueOf(LocalDateTime.parse(faq.get("datahora").asText())));
		faq2.setTexto(faq.get("texto").asText());
		Produto produto = produtoRepository.findById(faq.get("idProduto").asInt()).get();
		faq2.setProduto(produto);
		
		/*Faq faq = new Faq();
		faq.setDatahora(datahora);
		faq.setTexto(texto);
		Produto produto = produtoRepository.findById(idProduto).get();
		faq.setProduto(produto);*/
		try {
			faqRepository.save(faq2);
		}catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			// get the last node of the violation
			String field = "";
			for (Node node : violation.getPropertyPath()) {
			    field += node.getName();
			}
			return (ResponseEntity<T>) new ResponseEntity<String>("Falha no cadastro faq. Campo faltando:"+field,HttpStatus.BAD_REQUEST);
			/*throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro da faq.Campo faltando:"+field);*/
			//return new ResponseEntity<String>("Falha no cadastro da faq.Campo faltando:"+field,HttpStatus.BAD_REQUEST);
		}/*catch(UnexpectedTypeException e) {
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro da faq.:");
			return new ResponseEntity<String>("Falha no cadastro da faq.Esperava um tipo de campo na requisição e foi passado outro",HttpStatus.BAD_REQUEST);
		}*/
		return (ResponseEntity<T>) new ResponseEntity<Object>(faq,HttpStatus.CREATED);
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
	
	/*@PutMapping(path="/update/{id}")
	public @ResponseBody String updateFaqById(
			@RequestParam Timestamp datahora,
			@RequestParam String texto,
			@RequestParam Integer idProduto,
			@PathVariable("id")Integer id) {
		if(faqRepository.existsById(id)) {
			Produto produto = produtoRepository.findById(idProduto).get();
			Faq faq = new Faq();
			faq.setId(id);
			faq.setDatahora(datahora);
			faq.setTexto(texto);
			faq.setProduto(produto);
			faqRepository.save(faq);
			return "Faq atualizado com Sucesso!";
		}
		return "Faq não encontrado";
	}*/
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<T> updateFaqById(@RequestBody Faq faq,
			@PathVariable("id")Integer id) {
		if(faqRepository.existsById(id)) {
			faqRepository.save(faq);
			return (ResponseEntity<T>) new ResponseEntity<Faq>(faq,HttpStatus.OK);
		}
		return (ResponseEntity<T>) new ResponseEntity<String>("Falha na atualização faq",HttpStatus.BAD_REQUEST);
	}
}