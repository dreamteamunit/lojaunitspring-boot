package lojaunit.controller;

import java.sql.Date;
import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Path.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
import lojaunit.repository.ClienteRepository;

@Controller
@RequestMapping(path = "/clientes")
public class ClienteController {
	@Autowired
	private ClienteRepository clienteRepository;

	@PostMapping(path = "/add")
	public @ResponseBody String addNewCliente(@Valid @RequestParam String nome, @RequestParam String cpf,
			@RequestParam String email, @RequestParam Date dataNascimento, @RequestParam String sexo,
			@RequestParam String nomeSocial, @RequestParam String apelido, @RequestParam String telefone) {

		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setCpf(cpf);
		cliente.setEmail(email);
		cliente.setDataNascimento(dataNascimento);
		cliente.setSexo(sexo);
		cliente.setNomeSocial(nomeSocial);
		cliente.setApelido(apelido);
		cliente.setTelefone(telefone);
		try {
			clienteRepository.save(cliente);
		}catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			// get the last node of the violation
			String field = "";
			for (Node node : violation.getPropertyPath()) {
			    field += node.getName();
			}
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro do cliente.Campo faltando:"+field);
		}
		catch(DataIntegrityViolationException e) {
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro do cliente.Cpf já cadastrado");
		}
		return "Cliente Cadastrado com Sucesso!";
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

	@PutMapping(path = "/update/{id}")
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
	}
}
