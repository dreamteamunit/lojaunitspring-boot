package lojaunit.controller;

import java.sql.Timestamp;
import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import javax.validation.Valid;
import javax.validation.Path.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import lojaunit.entities.Cliente;
import lojaunit.entities.FormaPagamento;
import lojaunit.entities.Venda;
import lojaunit.repository.ClienteRepository;
import lojaunit.repository.FormaPagamentoRepository;
import lojaunit.repository.VendaRepository;

@Controller
@RequestMapping(path="/venda")
public class VendaController {
	@Autowired
	
	private VendaRepository vendaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@PostMapping(path="/add")
	public ResponseEntity<String> addNewVenda(@Valid
			@RequestParam Timestamp datahora,
			@RequestParam Double valorTotal,
			@RequestParam Integer idCliente,
			@RequestParam Integer idFormaPagamento) {
		
		Venda venda = new Venda();
		venda.setDatahora(datahora);
		venda.setValorTotal(valorTotal);
		Cliente cliente = clienteRepository.findById(idCliente).get();
		venda.setCliente(cliente);
		FormaPagamento formaPagamento = formaPagamentoRepository.findById(idFormaPagamento).get();
		venda.setFormaPagamento(formaPagamento);
		try {
			vendaRepository.save(venda);
		}catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			// get the last node of the violation
			String field = "";
			for (Node node : violation.getPropertyPath()) {
			    field += node.getName();
			}
			/*throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro da venda.Campo faltando:"+field);*/
			return new ResponseEntity<String>("Falha no cadastro da venda.Campo faltando:"+field,HttpStatus.BAD_REQUEST);
		}catch(UnexpectedTypeException e) {
			/*throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro da venda:");*/
			return new ResponseEntity<String>("Falha no cadastro da venda.Esperava um tipo de campo na requisição e foi passado outro",HttpStatus.BAD_GATEWAY);
		}
		return new ResponseEntity<String>("Venda realizada com Sucesso!",HttpStatus.CREATED);
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
	
	@PutMapping(path="/update/{id}")
	public @ResponseBody String updateVendaById(
			@RequestParam Timestamp datahora,
			@RequestParam Double valorTotal,
			@RequestParam Integer idCliente,
			@RequestParam Integer idFormaPagamento,
			@PathVariable("id")Integer id) {
		if(vendaRepository.existsById(id)) {
			Venda venda = new Venda();
			venda.setId(id);
			venda.setDatahora(datahora);
			venda.setValorTotal(valorTotal);
			Cliente cliente = clienteRepository.findById(idCliente).get();
			venda.setCliente(cliente);
			FormaPagamento formaPagamento = formaPagamentoRepository.findById(idFormaPagamento).get();
			venda.setFormaPagamento(formaPagamento);
			vendaRepository.save(venda);
			return "Venda atualizada com Sucesso!";
		}
		return "Venda não encontrada";
	}
}