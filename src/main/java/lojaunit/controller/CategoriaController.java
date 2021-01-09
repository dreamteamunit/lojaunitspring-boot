package lojaunit.controller;

import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;
import javax.validation.Valid;

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

import lojaunit.entities.Categoria;
import lojaunit.repository.CategoriaRepository;

@Controller
@RequestMapping(path="/categoria")
public class CategoriaController {
	@Autowired
	
	private CategoriaRepository categoriaRepository;
	
	@PostMapping(path="/add")
	public ResponseEntity<String> addNewCategoria(@Valid @RequestParam String nome, @RequestParam Boolean ativo) {
		Categoria categoria = new Categoria();
		categoria.setNome(nome);
		categoria.setAtivo(ativo);
		try {
			categoriaRepository.save(categoria);
		}catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			// get the last node of the violation
			String field = "";
			for (Node node : violation.getPropertyPath()) {
			    field += node.getName();
			}
			/*throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro da categoria.Campo faltando:"+field);*/
			return new ResponseEntity<String>("Falha no cadastro da categoria.Campo faltando:"+field,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Categoria cadastrada com Sucesso!",HttpStatus.CREATED);
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Categoria> getAllCategoria(){
		return categoriaRepository.findAll();
	}
	
	@GetMapping(path="/find/{id}")
	public @ResponseBody Optional<Categoria> getCategoriaById(@PathVariable("id")Integer id) {
		return categoriaRepository.findById(id);
	}
	
	@DeleteMapping(path="/delete/all")
	public @ResponseBody String deleteAll() {
		categoriaRepository.deleteAll();
		return "O conteúdo da Tabela Categoria foi apagado com Sucesso!";
	}
	
	@DeleteMapping(path="/delete/{id}")
	public @ResponseBody String deleteCategoriaById(@PathVariable("id")Integer id) {
		if(categoriaRepository.existsById(id)) {
			categoriaRepository.deleteById(id);
			return "Categoria apagada com sucesso";
		}
		return "Categoria não encontrada";
	}
	
	@PutMapping(path="/update/{id}")
	public @ResponseBody String updateCategoriaById(@RequestParam String nome, @RequestParam Boolean ativo,
			@PathVariable("id")Integer id) {
		if(categoriaRepository.existsById(id)) {
			Categoria categoria = new Categoria();
			categoria.setId(id);
			categoria.setNome(nome);
			categoria.setAtivo(ativo);
			categoriaRepository.save(categoria);
			return "Categoria atualizada com Sucesso!";
		}
		return "Categoria não encontrada";
	}
}