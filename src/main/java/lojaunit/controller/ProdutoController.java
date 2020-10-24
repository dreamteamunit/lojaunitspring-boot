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

import lojaunit.entities.Categoria;
import lojaunit.entities.Fornecedor;
import lojaunit.entities.Marca;
import lojaunit.entities.Produto;
import lojaunit.repository.ProdutoRepository;

@Controller
@RequestMapping(path="/produto")
public class ProdutoController {
	@Autowired
	
	private ProdutoRepository produtoRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewProduto(
			@RequestParam String nome,
			@RequestParam String descricao,
			@RequestParam Double precoUnitario,
			@RequestParam String unidade,
			@RequestParam Categoria categoria,
			@RequestParam Fornecedor fornecedor,
			@RequestParam Marca marca) {
		
		Produto produto = new Produto();
		produto.setNome(nome);
		produto.setDescricao(descricao);
		produto.setPrecoUnitario(precoUnitario);
		produto.setUnidade(unidade);
		produto.setCategoria(categoria);
		produto.setFornecedor(fornecedor);
		produto.setMarca(marca);
		produtoRepository.save(produto);
		return "Produto com Sucesso!";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Produto> getAllProdutos(){
		return produtoRepository.findAll();
	}
	
	@GetMapping(path="/find/{id}")
	public @ResponseBody Optional<Produto> getProdutoById(@PathVariable("id")Integer id){
		return produtoRepository.findById(id);
	}
	
	@DeleteMapping(path="/delete/{id}")
	public @ResponseBody String deleteBydId(@PathVariable("id")Integer id) {
		if(produtoRepository.existsById(id)) {
			produtoRepository.deleteById(id);
			return "Produto apagado com sucesso";
		}
		return "Produto não encontrado";
	}
	
	@DeleteMapping(path="/delete/all")
	public @ResponseBody String deleteAll() {
		produtoRepository.deleteAll();
		return "O conteúdo da Tabela Produto foi apagado com Sucesso!";
	}
}
