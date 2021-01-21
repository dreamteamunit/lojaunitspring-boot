package lojaunit.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
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
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import lojaunit.entities.Cliente;
import lojaunit.entities.Faq;
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
	
	@RequestMapping(value="/add", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<T> addNewVenda(@Valid
			@RequestBody ObjectNode venda
			/*@RequestParam Timestamp datahora,
			@RequestParam Double valorTotal,
			@RequestParam Integer idCliente,
			@RequestParam Integer idFormaPagamento*/) {
		
		Venda venda2 = new Venda();
		String data = venda.get("datahora").asText();
		venda2.setValorTotal(venda.get("valorTotal").asDouble());
		venda2.setDatahora(Timestamp.valueOf(LocalDateTime.parse(data)));
		Cliente cliente = clienteRepository.findById(venda.get("idCliente").asInt()).get();
		venda2.setCliente(cliente);
		FormaPagamento formaPag = formaPagamentoRepository.findById(venda.get("idFormaPagamento").asInt()).get();
		venda2.setFormaPagamento(formaPag);
		/*Venda venda = new Venda();
		venda.setDatahora(datahora);
		venda.setValorTotal(valorTotal);
		Cliente cliente = clienteRepository.findById(idCliente).get();
		venda.setCliente(cliente);
		FormaPagamento formaPagamento = formaPagamentoRepository.findById(idFormaPagamento).get();
		venda.setFormaPagamento(formaPagamento);*/
		try {
			vendaRepository.save(venda2);
		}catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			// get the last node of the violation
			String field = "";
			for (Node node : violation.getPropertyPath()) {
			    field += node.getName();
			}
			return (ResponseEntity<T>) new ResponseEntity<String>("Falha no cadastro Venda. Campo faltando:"+field,HttpStatus.BAD_REQUEST);
			/*throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro da venda.Campo faltando:"+field);*/
			//return new ResponseEntity<String>("Falha no cadastro da venda.Campo faltando:"+field,HttpStatus.BAD_REQUEST);
		}/*catch(UnexpectedTypeException e) {
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro da venda:");
			return new ResponseEntity<String>("Falha no cadastro da venda.Esperava um tipo de campo na requisição e foi passado outro",HttpStatus.BAD_GATEWAY);
		}*/
		return (ResponseEntity<T>) new ResponseEntity<Object>(venda,HttpStatus.CREATED);
		//return new ResponseEntity<String>("Venda realizada com Sucesso!",HttpStatus.CREATED);
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
	
	/*@PutMapping(path="/update/{id}")
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
	}*/
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<T> updateVendaById(@RequestBody Venda venda,
			@PathVariable("id")Integer id) {
		if(vendaRepository.existsById(id)) {
			vendaRepository.save(venda);
			return (ResponseEntity<T>) new ResponseEntity<Venda>(venda,HttpStatus.OK);
		}
		return (ResponseEntity<T>) new ResponseEntity<String>("Falha na atualização venda",HttpStatus.BAD_REQUEST);
	}
}