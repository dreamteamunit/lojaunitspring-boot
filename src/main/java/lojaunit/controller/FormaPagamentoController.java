package lojaunit.controller;

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

import lojaunit.entities.FormaPagamento;
import lojaunit.repository.FormaPagamentoRepository;

@Controller
@RequestMapping(path="/formapagamento")
public class FormaPagamentoController {
	@Autowired
	
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewFormaPagamento (
			@RequestParam String forma,
			@RequestParam String descricao,
			@RequestParam Boolean ativo) {
		
		FormaPagamento formaPagamento = new FormaPagamento();
		formaPagamento.setForma(forma);
		formaPagamento.setDescricao(descricao);
		formaPagamento.setAtivo(ativo);
		formaPagamentoRepository.save(formaPagamento);
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
		if(getFormaPagamentoById(id) != null) {
			formaPagamentoRepository.deleteById(id);
			return "Forma de Pagamento apagada com sucesso";
		}
		return "Forma de Pagamento não encontrada";
	}
}
