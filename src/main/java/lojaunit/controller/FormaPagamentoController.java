package lojaunit.controller;

import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Path.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import lojaunit.entities.FormaPagamento;
import lojaunit.repository.FormaPagamentoRepository;

@Controller
@RequestMapping(path="/formapagamento")
public class FormaPagamentoController {
	@Autowired
	
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewFormaPagamento (@Valid
			@RequestParam String forma,
			@RequestParam String descricao,
			@RequestParam Boolean ativo) {
		
		FormaPagamento formaPagamento = new FormaPagamento();
		formaPagamento.setForma(forma);
		formaPagamento.setDescricao(descricao);
		formaPagamento.setAtivo(ativo);
		try {
			formaPagamentoRepository.save(formaPagamento);
		}catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			// get the last node of the violation
			String field = "";
			for (Node node : violation.getPropertyPath()) {
			    field += node.getName();
			}
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro da forma de pagamento.Campo faltando:"+field);
		}
		return "Forma de Pagamento cadastrada com sucesso";
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
	
	@PutMapping(path="/update/{id}")
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
	}
}