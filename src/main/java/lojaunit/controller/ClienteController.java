package lojaunit.controller;

import java.sql.Date;
import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import lojaunit.entities.Cliente;
import lojaunit.repository.ClienteRepository;

@Controller
@RequestMapping(path = "/clientes")
public class ClienteController {
	@Autowired
	private ClienteRepository clienteRepository;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/add", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<T> addNewCliente(@Valid
			@RequestBody Cliente cliente
			/*@RequestParam String nome, @RequestParam String cpf,
			@RequestParam String email, @RequestParam Date dataNascimento, @RequestParam String sexo,
			@RequestParam String nomeSocial, @RequestParam String apelido, @RequestParam String telefone*/) {

		/*Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setCpf(cpf);
		cliente.setEmail(email);
		cliente.setDataNascimento(dataNascimento);
		cliente.setSexo(sexo);
		cliente.setNomeSocial(nomeSocial);
		cliente.setApelido(apelido);
		cliente.setTelefone(telefone);*/
		try {
			clienteRepository.save(cliente);
		}catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			// get the last node of the violation
			String field = "";
			for (Node node : violation.getPropertyPath()) {
			    field += node.getName();
			} 
			return (ResponseEntity<T>) new ResponseEntity<String>("Falha no cadastro do cliente. Campo faltando:"+field,HttpStatus.BAD_REQUEST);
			/*throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro do cliente.Campo faltando:"+field);*/
			//return new ResponseEntity<String>("Falha no cadastro do cliente.Campo faltando:"+field,HttpStatus.BAD_REQUEST);
		}
		/*catch(DataIntegrityViolationException e) {
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro do cliente.Cpf já cadastrado");
			return new ResponseEntity<String>("Falha no cadastro do cliente.Cpf já cadastrado",HttpStatus.BAD_GATEWAY);
		}*/
		//return new ResponseEntity<String>("Cliente Cadastrado com Sucesso!",HttpStatus.CREATED);
		return (ResponseEntity<T>) new ResponseEntity<Cliente>(cliente,HttpStatus.CREATED);
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Cliente> getAllClientes() {
		return clienteRepository.findAll();
	}

	@GetMapping(path = "/find/{id}")
	public @ResponseBody Optional<Cliente> getClienteById(@PathVariable("id") Integer id) {
		return clienteRepository.findById(id);
	}

	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody String deleteClienteById(@PathVariable("id") Integer id) {
		if (clienteRepository.existsById(id)) {
			clienteRepository.deleteById(id);
			return "Cliente apagado com Sucesso";
		}
		return "Cliente não encontrado!";
	}

	@DeleteMapping(path = "/delete/all")
	public @ResponseBody String deleteAll() {
		clienteRepository.deleteAll();
		return "O conteúdo da Tabela Clientes foi apagado com Sucesso!";
	}

	/*@PutMapping(path = "/update/{id}")
	public @ResponseBody String updateClienteById(@RequestParam String nome, @RequestParam String cpf,
			@RequestParam String email, @RequestParam Date dataNascimento, @RequestParam String sexo,
			@RequestParam String nomeSocial, @RequestParam String apelido, @RequestParam String telefone,
			@PathVariable("id") Integer id) {

		if (clienteRepository.existsById(id)) {
			Cliente cliente = new Cliente();
			cliente.setId(id);
			cliente.setNome(nome);
			cliente.setCpf(cpf);
			cliente.setEmail(email);
			cliente.setDataNascimento(dataNascimento);
			cliente.setSexo(sexo);
			cliente.setNomeSocial(nomeSocial);
			cliente.setApelido(apelido);
			cliente.setTelefone(telefone);
			clienteRepository.save(cliente);
			return "Cliente atualizado com Sucesso!";
		}

		return "Cliente não encontrado";
	}*/
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<T> updateClienteById(@RequestBody Cliente cliente, @PathVariable("id") Integer id){
		if(clienteRepository.existsById(id)) {
			clienteRepository.save(cliente);
			return (ResponseEntity<T>) new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
		}
		return (ResponseEntity<T>) new ResponseEntity<String>("Falha na atualização do cliente",HttpStatus.BAD_REQUEST);
	}
}