package lojaunit.controller;

import java.sql.Date;
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

import lojaunit.entities.Cliente;
import lojaunit.repository.ClienteRepository;

@Controller
@RequestMapping(path = "/clientes")
public class ClienteController {
	@Autowired
	private ClienteRepository clienteRepository;

	@PostMapping(path = "/add")
	public @ResponseBody String addNewCliente(@RequestParam String nome, @RequestParam String cpf,
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
		clienteRepository.save(cliente);
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

	@PutMapping(path = "/alterar/{id}")
	public @ResponseBody String alterarCliente(@RequestParam String nome, @RequestParam String cpf,
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
		}

		return "Cliente atualizado com Sucesso!";
	}
}
