package lojaunit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
			@RequestParam Integer id,
			@RequestParam String forma,
			@RequestParam String descricao,
			@RequestParam Boolean ativo) {
		
		FormaPagamento formaPagamento = new FormaPagamento();
		formaPagamento.setId(id);
		formaPagamento.setForma(forma);
		formaPagamento.setDescricao(descricao);
		formaPagamento.setAtivo(ativo);
		formaPagamentoRepository.save(formaPagamento);
		return "Forma de Pagamento";
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<FormaPagamento> getAllFormaPagamentos(){
		return formaPagamentoRepository.findAll();
	}
}
