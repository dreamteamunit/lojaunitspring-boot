package lojaunit.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lojaunit.entities.Marca;
import lojaunit.repository.MarcaRepository;

@Controller
@RequestMapping(path="/marca")
public class MarcaController {
	@Autowired
	
	private MarcaRepository marcaRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewMarca(
			@RequestParam String nome,
			@RequestParam String descricao) {
		
		Marca marca = new Marca();
		marca.setNome(nome);
		marca.setDescricao(descricao);
		marcaRepository.save(marca);
		return "Marca cadastrada com Sucesso!";
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
		if(getMarcaById(id) != null) {
			marcaRepository.deleteById(id);
			return "Marca apagada com Sucesso";
		}
		return "Marca não encontrada";
	}
}
