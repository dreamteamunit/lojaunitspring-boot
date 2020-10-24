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

import lojaunit.entities.Cliente;
import lojaunit.entities.FormaPagamento;
import lojaunit.entities.Venda;
import lojaunit.repository.VendaRepository;

@Controller
@RequestMapping(path="/venda")
public class VendaController {
	@Autowired
	
	private VendaRepository vendaRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewVenda(
			@RequestParam Timestamp datahora,
			@RequestParam Double valorTotal,
			@RequestParam Cliente cliente,
			@RequestParam FormaPagamento formaPagamento) {
		
		Venda venda = new Venda();
		venda.setDatahora(datahora);
		venda.setValorTotal(valorTotal);
		venda.setCliente(cliente);
		venda.setFormaPagamento(formaPagamento);
		vendaRepository.save(venda);
		return "Venda com Sucesso!";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Venda> getAllVendas(){
		return vendaRepository.findAll();
	}
	
	@GetMapping(path="/find/{id}")
	public @ResponseBody Optional<Venda> getVendaById(@PathVariable("id")Integer id){
		return vendaRepository.findById(id);
	}
	
	@DeleteMapping(path="/delete/all")
	public @ResponseBody String deleteAll() {
		vendaRepository.deleteAll();
		return "O conteúdo da Tabela Venda foi apagado com Sucesso!";
	}
	
	@DeleteMapping(path="/delete/{id}")
	public @ResponseBody String deleteVendaById(@PathVariable("id")Integer id) {
		if(vendaRepository.existsById(id)) {
			vendaRepository.deleteById(id);
			return "Venda apagada com sucesso";
		}
		return "Venda não encontrada";
	}
}
