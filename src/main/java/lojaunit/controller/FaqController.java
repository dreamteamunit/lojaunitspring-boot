package lojaunit.controller;

import java.sql.Timestamp;
import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Path.Node;
import javax.validation.UnexpectedTypeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import lojaunit.entities.Faq;
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
	
	@PostMapping(path="/add")
	public ResponseEntity<String> addNewFaq(@Valid
			@RequestParam Timestamp datahora,
			@RequestParam String texto,
			@RequestParam Integer idProduto
			) {
		Faq faq = new Faq();
		faq.setDatahora(datahora);
		faq.setTexto(texto);
		Produto produto = produtoRepository.findById(idProduto).get();
		faq.setProduto(produto);
		try {
			faqRepository.save(faq);
		}catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			// get the last node of the violation
			String field = "";
			for (Node node : violation.getPropertyPath()) {
			    field += node.getName();
			}
			/*throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro da faq.Campo faltando:"+field);*/
			return new ResponseEntity<String>("Falha no cadastro da faq.Campo faltando:"+field,HttpStatus.BAD_REQUEST);
		}catch(UnexpectedTypeException e) {
			/*throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro da faq.:");*/
			return new ResponseEntity<String>("Falha no cadastro da faq.Esperava um tipo de campo na requisição e foi passado outro",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Faq Cadastrado com Sucesso!",HttpStatus.CREATED);
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
	
	@PutMapping(path="/update/{id}")
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
	}
}