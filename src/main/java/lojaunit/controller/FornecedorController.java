package lojaunit.controller;

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
import org.springframework.validation.BindingResult;
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

import lojaunit.entities.Fornecedor;
import lojaunit.entities.Marca;
import lojaunit.repository.FornecedorRepository;

@Controller
@RequestMapping(path="/fornecedor")
public class FornecedorController {
	@Autowired
	
	private FornecedorRepository fornecedorRepository;
	
	@RequestMapping(value="/add", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<T> addNewFornecedor(@Valid
			@RequestBody Fornecedor fornecedor
			/*@RequestParam String nome,
			@RequestParam String endereco,
			@RequestParam String telefone,
			@RequestParam String cnpj,
			@RequestParam String email*/) {
		
		/*Fornecedor fornecedor = new Fornecedor();
		fornecedor.setNome(nome);
		fornecedor.setEndereco(endereco);
		fornecedor.setTelefone(telefone);
		fornecedor.setCnpj(cnpj);
		fornecedor.setEmail(email);*/
		try {
			fornecedorRepository.save(fornecedor);
		}catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			// get the last node of the violation
			String field = "";
			for (Node node : violation.getPropertyPath()) {
			    field += node.getName();
			}
			return (ResponseEntity<T>) new ResponseEntity<String>("Falha no cadastro de fornecedor.Campo faltando:"+field,HttpStatus.BAD_REQUEST);
			/*throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro de fornecedor.Campo faltando:"+field);*/
			//return new ResponseEntity<String>("Falha no cadastro de fornecedor.Campo faltando:"+field,HttpStatus.BAD_REQUEST);
		/*}catch(DataIntegrityViolationException e) {
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro do fornecedor.Cnpj já cadastrado");
			return new ResponseEntity<String>("Falha no cadastro do fornecedor.Cnpj já cadastrado",HttpStatus.BAD_REQUEST);*/
		}
		return (ResponseEntity<T>) new ResponseEntity<Fornecedor>(fornecedor,HttpStatus.CREATED);
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Fornecedor> getAllFornecedores(){
		return fornecedorRepository.findAll();
	}
	
	@GetMapping(path="/find/{id}")
	public @ResponseBody Optional<Fornecedor> getFornecedorById(@PathVariable("id")Integer id){
		return fornecedorRepository.findById(id);
	}
	
	@DeleteMapping(path="/delete/all")
	public @ResponseBody String deleteAll() {
		fornecedorRepository.deleteAll();
		return "O conteúdo da Tabela Fornecedor foi apagado com Sucesso!";
	}
	
	@DeleteMapping(path="/delete/{id}")
	public @ResponseBody String deleteFornecedorById(@PathVariable("id")Integer id) {
		if(fornecedorRepository.existsById(id)) {
			fornecedorRepository.deleteById(id);
			return "Fornecedor apagado com sucesso";
		}
		return "Fornecedor não encontrado";
	}
	
	/*@PutMapping(path="/update/{id}")
	public @ResponseBody String updateFornecedorById(
			@RequestParam String nome,
			@RequestParam String endereco,
			@RequestParam String telefone,
			@RequestParam String cnpj,
			@RequestParam String email,
			@PathVariable("id")Integer id) {
		if(fornecedorRepository.existsById(id)) {
			Fornecedor fornecedor = new Fornecedor();
			fornecedor.setId(id);
			fornecedor.setNome(nome);
			fornecedor.setEndereco(endereco);
			fornecedor.setTelefone(telefone);
			fornecedor.setCnpj(cnpj);
			fornecedor.setEmail(email);
			fornecedorRepository.save(fornecedor);
			return "Fornecedor atualizado com Sucesso!";
		}
		return "Fornecedor não encontrado";
	}*/
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<T> updateFornecedorById(@RequestBody Fornecedor fornecedor, @PathVariable("id") Integer id){
		if(fornecedorRepository.existsById(id)) {
			fornecedorRepository.save(fornecedor);
			return (ResponseEntity<T>) new ResponseEntity<Fornecedor>(fornecedor,HttpStatus.OK);
		}
		return (ResponseEntity<T>) new ResponseEntity<String>("Falha na atualização do fornecedor",HttpStatus.BAD_REQUEST);
	}
}