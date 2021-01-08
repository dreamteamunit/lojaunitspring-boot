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
import org.springframework.web.server.ResponseStatusException;

import lojaunit.entities.Marca;
import lojaunit.repository.MarcaRepository;

@Controller
@RequestMapping(path="/marca")
public class MarcaController {
	@Autowired
	
	private MarcaRepository marcaRepository;
	
	@RequestMapping(value="/add", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<T> addNewMarca(@Valid
			@RequestBody(required = false) Marca marca
			//@RequestParam String nome,
			//@RequestParam String descricao
			) {
		Marca salva = null;
		try {
			//Marca marca = new Marca();
			//marca.setNome(nome);
			//marca.setDescricao(descricao);
			salva = marcaRepository.save(marca);
		/*}catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			// get the last node of the violation
			String field = "";
			for (Node node : violation.getPropertyPath()) {
			    field += node.getName();
			}
			Marca marca2 = new Marca();
			marca2.setDescricao("Falha no cadastro da marca.Campo faltando:"+field);
			return (ResponseEntity<T>) new ResponseEntity<Marca>(marca2,HttpStatus.BAD_REQUEST);
			/*throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro da marca.Campo faltando:"+field);*/
		}catch(Exception e) {
			Marca marca2 = new Marca();
			marca2.setDescricao("Falha no cadastro da marca");
			return (ResponseEntity<T>) new ResponseEntity<Marca>(marca2,HttpStatus.BAD_REQUEST);
		}
		/*if(salva==null) {
			Marca marca2 = new Marca();
			marca2.setDescricao("Falha no cadastro da marca");
			return (ResponseEntity<T>) new ResponseEntity<Marca>(marca2,HttpStatus.BAD_REQUEST);
		}*/
		return (ResponseEntity<T>) new ResponseEntity<Marca>(marca,HttpStatus.CREATED); 
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Marca> getAllMarca(){
		return marcaRepository.findAll();
	}
	
	@GetMapping(path="/find/{id}")
	public @ResponseBody Optional<Marca> getMarcaById(@PathVariable("id")Integer id){
		return marcaRepository.findById(id);
	}
	
	@DeleteMapping(path="/delete/all")
	public @ResponseBody String deleteAll() {
		marcaRepository.deleteAll();
		return "O conteúdo da Marcas Clientes foi apagado com Sucesso!";
	}
	
	@DeleteMapping(path="/delete/{id}")
	public @ResponseBody String deleteMarcaById(@PathVariable("id")Integer id) {
		if(marcaRepository.existsById(id)) {
			marcaRepository.deleteById(id);
			return "Marca apagada com Sucesso";
		}
		return "Marca não encontrada";
	}
	
	@PutMapping(path="/update/{id}")
	public @ResponseBody String updateMarcaById(@RequestParam String nome, @RequestParam String descricao,
			@PathVariable("id")Integer id) {
		if(marcaRepository.existsById(id)) {
			Marca marca = new Marca();
			marca.setId(id);
			marca.setNome(nome);
			marca.setDescricao(descricao);
			marcaRepository.save(marca);
			return "Marca atualizada com Sucesso!";
		}
		return "Marca não encotrada";
	}
}