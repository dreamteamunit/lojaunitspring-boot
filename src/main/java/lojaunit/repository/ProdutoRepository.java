package lojaunit.repository;

import org.springframework.data.repository.CrudRepository;

import lojaunit.entities.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer> {

}
