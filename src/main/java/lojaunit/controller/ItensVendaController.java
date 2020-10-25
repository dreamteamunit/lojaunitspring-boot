package lojaunit.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lojaunit.entities.ItensVenda;
import lojaunit.entities.Produto;
import lojaunit.entities.Venda;
import lojaunit.repository.ItensVendaRepository;
import lojaunit.repository.ProdutoRepository;
import lojaunit.repository.VendaRepository;

@Controller
@RequestMapping(path="/itensvenda")
public class ItensVendaController {
	@Autowired
	
	private ItensVendaRepository itensVendaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewItensVenda (
			@RequestParam Integer quantidade,
			@RequestParam Double valorUnitario,
			@RequestParam Integer idVenda,
			@RequestParam Integer idProduto) {
		
		ItensVenda itensVenda = new ItensVenda();
		itensVenda.setQuantidade(quantidade);
		itensVenda.setValorUnitario(valorUnitario);
		Venda venda = vendaRepository.findById(idVenda).get();
		itensVenda.setVenda(venda);
		Produto produto = produtoRepository.findById(idProduto).get();
		itensVenda.setProduto(produto);
		itensVendaRepository.save(itensVenda);
		return "Itens Venda cadastrado com Sucesso!";
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
	
	@PutMapping(path="/update/{id}")
	public @ResponseBody String updateItensVendaById(
			@RequestParam Integer quantidade,
			@RequestParam Double valorUnitario,
			@RequestParam Integer idVenda,
			@RequestParam Integer idProduto,
			@PathVariable("id")Integer id) {
		if(itensVendaRepository.existsById(id)) {
			Venda venda = vendaRepository.findById(idVenda).get();
			ItensVenda itensVenda = new ItensVenda();
			Produto produto = produtoRepository.findById(idProduto).get();
			itensVenda.setQuantidade(quantidade);
			itensVenda.setValorUnitario(valorUnitario);
			itensVenda.setVenda(venda);
			itensVenda.setProduto(produto);
			itensVendaRepository.save(itensVenda);
			return "Itens Venda atualizado com Sucesso!";
		}
		return "Itens não encontrado";
	}
}
