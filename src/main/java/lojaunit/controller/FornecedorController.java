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

import lojaunit.entities.Fornecedor;
import lojaunit.repository.FornecedorRepository;

@Controller
@RequestMapping(path="/fornecedor")
public class FornecedorController {
	@Autowired
	
	private FornecedorRepository fornecedorRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewFornecedor(
			@RequestParam String nome,
			@RequestParam String endereco,
			@RequestParam String telefone,
			@RequestParam String cnpj,
			@RequestParam String email) {
		
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setNome(nome);
		fornecedor.setEndereco(endereco);
		fornecedor.setTelefone(telefone);
		fornecedor.setCnpj(cnpj);
		fornecedor.setEmail(email);
		fornecedorRepository.save(fornecedor);
		return "Fornecedor Salvo com Sucesso!";
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
}
