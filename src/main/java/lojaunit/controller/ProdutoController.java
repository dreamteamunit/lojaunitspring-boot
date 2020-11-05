package lojaunit.controller;

import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Path.Node;

import org.springframework.beans.factory.annotation.Autowired;
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

import lojaunit.entities.Categoria;
import lojaunit.entities.Fornecedor;
import lojaunit.entities.Marca;
import lojaunit.entities.Produto;
import lojaunit.repository.CategoriaRepository;
import lojaunit.repository.FornecedorRepository;
import lojaunit.repository.MarcaRepository;
import lojaunit.repository.ProdutoRepository;

@Controller
@RequestMapping(path="/produto")
public class ProdutoController {
	@Autowired
	
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private FornecedorRepository fornecedorRepository;
	@Autowired
	private MarcaRepository marcaRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewProduto(@Valid
			@RequestParam String nome,
			@RequestParam String descricao,
			@RequestParam Double precoUnitario,
			@RequestParam String unidade,
			@RequestParam Integer idCategoria,
			@RequestParam Integer idFornecedor,
			@RequestParam Integer idMarca) {
		
		Produto produto = new Produto();
		produto.setNome(nome);
		produto.setDescricao(descricao);
		produto.setPrecoUnitario(precoUnitario);
		produto.setUnidade(unidade);
		Categoria categoria = categoriaRepository.findById(idCategoria).get();
		produto.setCategoria(categoria);
		Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor).get();
		produto.setFornecedor(fornecedor);
		Marca marca = marcaRepository.findById(idMarca).get();
		produto.setMarca(marca);
		try {
			produtoRepository.save(produto);
		}catch(ConstraintViolationException e) {
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			// get the last node of the violation
			String field = "";
			for (Node node : violation.getPropertyPath()) {
			    field += node.getName();
			}
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Falha no cadastro do produto.Campo faltando:"+field);
		}
		return "Produto cadastrado com Sucesso!";
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
	
	@PutMapping(path="/update/{id}")
	public @ResponseBody String updateProdutoById(
			@RequestParam String nome,
			@RequestParam String descricao,
			@RequestParam Double precoUnitario,
			@RequestParam String unidade,
			@RequestParam Integer idCategoria,
			@RequestParam Integer idFornecedor,
			@RequestParam Integer idMarca,
			@PathVariable("id")Integer id) {
		if(produtoRepository.existsById(id)) {
			Produto produto = new Produto();
			produto.setId(id);
			produto.setNome(nome);
			produto.setDescricao(descricao);
			produto.setPrecoUnitario(precoUnitario);
			produto.setUnidade(unidade);
			Categoria categoria = categoriaRepository.findById(idCategoria).get();
			produto.setCategoria(categoria);
			Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor).get();
			produto.setFornecedor(fornecedor);
			Marca marca = marcaRepository.findById(idMarca).get();
			produto.setMarca(marca);
			produtoRepository.save(produto);
			return "Produto com Sucesso!";
		}
		return "Produto não encontrado";
	}
}