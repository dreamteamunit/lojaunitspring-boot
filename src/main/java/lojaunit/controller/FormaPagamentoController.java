package lojaunit.controller;

import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;
import javax.validation.Valid;

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

import lojaunit.entities.FormaPagamento;
import lojaunit.entities.Marca;
import lojaunit.repository.FormaPagamentoRepository;

@Controller
@RequestMapping(path="/formapagamento")
public class FormaPagamentoController {
	@Autowired
	
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@RequestMapping(value="/add", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<T> addNewFormaPagamento (@Valid
			@RequestBody FormaPagamento formaPagamento
			/*@RequestParam String forma,
			@RequestParam String descricao,
			@RequestParam Boolean ativo*/) {
		
		/*FormaPagamento formaPagamento = new FormaPagamento();
		formaPagamento.setForma(forma);
		formaPagamento.setDescricao(descricao);
		formaPagamento.setAtivo(ativo);*/
		try {
			formaPagamentoRepository.save(formaPagamento);
		}catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			// get the last node of the violation
			String field = "";
			for (Node node : violation.getPropertyPath()) {
			    field += node.getName();
			}
			return (ResponseEntity<T>) new ResponseEntity<String>("Falha no cadastro da forma de pagamento. Campo faltando:"+field,HttpStatus.BAD_REQUEST);
			/*throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro da forma de pagamento.Campo faltando:"+field);
			return new ResponseEntity<String>("Falha no cadastro da forma de pagamento.Campo faltando:"+field,HttpStatus.BAD_REQUEST);*/
		}
		return (ResponseEntity<T>) new ResponseEntity<FormaPagamento>(formaPagamento,HttpStatus.CREATED);
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<FormaPagamento> getAllFormaPagamentos(){
		return formaPagamentoRepository.findAll();
	}
	
	@GetMapping(path="/find/{id}")
	public @ResponseBody Optional<FormaPagamento> getFormaPagamentoById(@PathVariable("id")Integer id){
		return formaPagamentoRepository.findById(id);
	}
	
	@DeleteMapping(path="/delete/all")
	public @ResponseBody String deleteAll() {
		formaPagamentoRepository.deleteAll();
		return "O conteúdo da Tabela Forma de Pagamento foi apagado com Sucesso!";
	}
	
	@DeleteMapping(path="/delete/{id}")
	public @ResponseBody String deleteFormaPagamentoById(@PathVariable("id")Integer id) {
		if(formaPagamentoRepository.existsById(id)) {
			formaPagamentoRepository.deleteById(id);
			return "Forma de Pagamento apagada com sucesso";
		}
		return "Forma de Pagamento não encontrada";
	}
	
	/*@PutMapping(path="/update/{id}")
	public @ResponseBody String updateFormaPagamentoById(
			@RequestParam String forma,
			@RequestParam String descricao,
			@RequestParam Boolean ativo,
			@PathVariable("id")Integer id) {
		if(formaPagamentoRepository.existsById(id)) {
			FormaPagamento formaPagamento = new FormaPagamento();
			formaPagamento.setId(id);
			formaPagamento.setForma(forma);
			formaPagamento.setDescricao(descricao);
			formaPagamento.setAtivo(ativo);
			formaPagamentoRepository.save(formaPagamento);
			return "Forma de Pagamento atualizada com Sucesso!";
		}
		return "Forma de Pagamento não encotrada";
	}*/
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<T> updateFormaPagamentoById(@RequestBody FormaPagamento formaPagamento,
			@PathVariable("id")Integer id) {
		if(formaPagamentoRepository.existsById(id)) {
			formaPagamentoRepository.save(formaPagamento);
			return (ResponseEntity<T>) new ResponseEntity<FormaPagamento>(formaPagamento,HttpStatus.OK);
		}
		return (ResponseEntity<T>) new ResponseEntity<String>("Falha na atualização da forma de pagamento",HttpStatus.BAD_REQUEST);
	}
}