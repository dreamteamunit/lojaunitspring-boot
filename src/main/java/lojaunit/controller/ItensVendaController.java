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

import lojaunit.entities.ItensVenda;
import lojaunit.entities.Produto;
import lojaunit.entities.Venda;
import lojaunit.repository.ItensVendaRepository;

@Controller
@RequestMapping(path="/itensvenda")
public class ItensVendaController {
	@Autowired
	
	private ItensVendaRepository itensVendaRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewItensVenda (
			@RequestParam Integer quantidade,
			@RequestParam Double valorUnitario,
			@RequestParam Venda venda,
			@RequestParam Produto produto) {
		
		ItensVenda itensVenda = new ItensVenda();
		itensVenda.setQuantidade(quantidade);
		itensVenda.setValorUnitario(valorUnitario);
		itensVenda.setVenda(venda);
		itensVenda.setProduto(produto);
		itensVendaRepository.save(itensVenda);
		return "Itens Venda";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<ItensVenda> getAllItensVenda(){
		return itensVendaRepository.findAll();
	}
	
	@GetMapping(path="/find/{id}")
	public @ResponseBody Optional<ItensVenda> getItensVendaById(@PathVariable("id")Integer id){
		return itensVendaRepository.findById(id);
	}
	
	@DeleteMapping(path="/delete/all")
	public @ResponseBody String deleteAll() {
		itensVendaRepository.deleteAll();
		return "O conteúdo da Tabela ItensVenda foi apagado com Sucesso!";
	}
	
	@DeleteMapping(path="/delete/{id}")
	public @ResponseBody String deleteItensVendaById(@PathVariable("id")Integer id) {
		if(itensVendaRepository.existsById(id)) {
			itensVendaRepository.deleteById(id);
			return "ItensVenda apagado com sucesso";
		}
		return "ItensVenda não encontrado";
	}
}
