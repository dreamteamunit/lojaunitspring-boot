package lojaunit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
			@RequestParam Integer id,
			@RequestParam String nome,
			@RequestParam String endereco,
			@RequestParam String telefone,
			@RequestParam String cnpj,
			@RequestParam String email) {
		
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setId(id);
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
}
