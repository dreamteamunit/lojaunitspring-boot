package lojaunit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lojaunit.entities.ItensVenda;
import lojaunit.repository.ItensVendaRepository;

@Controller
@RequestMapping(path="/itensvenda")
public class ItensVendaController {
	@Autowired
	
	private ItensVendaRepository itensVendaRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewItensVenda (
			@RequestParam Integer quantidade,
			@RequestParam Double valorUnitario) {
		
		ItensVenda itensVenda = new ItensVenda();
		itensVenda.setQuantidade(quantidade);
		itensVenda.setValorUnitario(valorUnitario);
		itensVendaRepository.save(itensVenda);
		return "Itens Venda";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<ItensVenda> getAllItensVenda(){
		return itensVendaRepository.findAll();
	}
}
