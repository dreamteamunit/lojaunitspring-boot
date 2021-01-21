package lojaunit.controller;

import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Path.Node;

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

import com.fasterxml.jackson.databind.node.ObjectNode;

import lojaunit.entities.Faq;
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
	
	@RequestMapping(value="/add", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<T> addNewItensVenda (@Valid
			@RequestBody ObjectNode itens
			/*@RequestParam Integer quantidade,
			@RequestParam Double valorUnitario,
			@RequestParam Integer idVenda,
			@RequestParam Integer idProduto*/) {
		
		ItensVenda itens2 = new ItensVenda();
		itens2.setQuantidade(itens.get("quantidade").asInt());
		itens2.setValorUnitario(itens.get("valorUnitario").asDouble());
		Venda venda = vendaRepository.findById(itens.get("idVenda").asInt()).get();
		itens2.setVenda(venda);
		Produto produto = produtoRepository.findById(itens.get("idProduto").asInt()).get();
		itens2.setProduto(produto);
		/*ItensVenda itensVenda = new ItensVenda();
		itensVenda.setQuantidade(quantidade);
		itensVenda.setValorUnitario(valorUnitario);
		Venda venda = vendaRepository.findById(idVenda).get();
		itensVenda.setVenda(venda);
		Produto produto = produtoRepository.findById(idProduto).get();
		itensVenda.setProduto(produto);
		itensVendaRepository.save(itensVenda);*/
		//return "Itens Venda cadastrado com Sucesso!";
		try {
			itensVendaRepository.save(itens2);
		}catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			// get the last node of the violation
			String field = "";
			for (Node node : violation.getPropertyPath()) {
			    field += node.getName();
			}
			return (ResponseEntity<T>) new ResponseEntity<String>("Falha no cadastro itensVenda. Campo faltando:"+field,HttpStatus.BAD_REQUEST);
		}
		return (ResponseEntity<T>) new ResponseEntity<Object>(itens,HttpStatus.CREATED);
	}
		
	@GetMapping(path="/all")
	public @ResponseBody Iterable<ItensVenda> getAllItensVenda(){
		return itensVendaRepository.findAll();
	}
	
	@GetMapping(path="/find/venda/{idVenda}/produto/{idProduto}")
	public @ResponseBody Optional<ItensVenda> getItensVendaById(@PathVariable("idVenda")Integer idVenda,@PathVariable("idProduto")Integer idProduto){
		return itensVendaRepository.findByIdVendaAndIdProduto(idVenda, idProduto);
	}
	
	@DeleteMapping(path="/delete/all")
	public @ResponseBody String deleteAll() {
		itensVendaRepository.deleteAll();
		return "O conteúdo da Tabela ItensVenda foi apagado com Sucesso!";
	}
	
	@DeleteMapping(path="/delete/venda/{idVenda}/produto/{idProduto}")
	public @ResponseBody String deleteItensVendaById(@PathVariable Integer idVenda,
			@PathVariable Integer idProduto) {
		ItensVenda itensVenda2 =itensVendaRepository.findByIdVendaAndIdProduto(idVenda, idProduto).get(); 
		if(itensVenda2!=null) {
			itensVendaRepository.deleteByIds(idVenda,idProduto);
			return "ItensVenda apagado com sucesso";
		}
		return "ItensVenda não encontrado";
	}
	
	/*@PutMapping(path="/update/venda/{idVenda}/produto/{idProduto}")
	public @ResponseBody String updateItensVendaById(
			@RequestParam Integer quantidade,
			@RequestParam Double valorUnitario,
			@RequestParam Integer idVenda,
			@RequestParam Integer idProduto) {
		ItensVenda itensVenda2 =itensVendaRepository.findByIdVendaAndIdProduto(idVenda, idProduto).get(); 
		if(itensVenda2!=null) {
			//Venda venda = vendaRepository.findById(idVenda).get();
			Venda venda = itensVenda2.getVenda();
			Produto produto = itensVenda2.getProduto();
			ItensVenda itensVenda = new ItensVenda();
			itensVenda.setQuantidade(quantidade);
			itensVenda.setValorUnitario(valorUnitario);
			itensVenda.setVenda(venda);
			itensVenda.setProduto(produto);
			itensVendaRepository.save(itensVenda);
			return "Itens Venda atualizado com Sucesso!";
		}
		return "Itens não encontrado";
	}*/
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<T> updateFaqById(@RequestBody ItensVenda itensVenda,
			@PathVariable("id")Integer id) {
		if(itensVendaRepository.existsById(id)) {
			itensVendaRepository.save(itensVenda);
			return (ResponseEntity<T>) new ResponseEntity<ItensVenda>(itensVenda,HttpStatus.OK);
		}
		return (ResponseEntity<T>) new ResponseEntity<String>("Falha na atualização itensVenda",HttpStatus.BAD_REQUEST);
	}
}