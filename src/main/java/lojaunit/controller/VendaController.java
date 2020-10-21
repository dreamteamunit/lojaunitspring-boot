package lojaunit.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lojaunit.entities.Venda;
import lojaunit.repository.VendaRepository;

@Controller
@RequestMapping(path="/venda")
public class VendaController {
	@Autowired
	
	private VendaRepository vendaRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewVenda(
			@RequestParam Integer id,
			@RequestParam Date datahora,
			@RequestParam Double valorTotal) {
		
		Venda venda = new Venda();
		venda.setId(id);
		venda.setDatahora(datahora);
		venda.setValorTotal(valorTotal);
		vendaRepository.save(venda);
		return "Venda com Sucesso!";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Venda> getAllVendas(){
		return vendaRepository.findAll();
	}
}
