package lojaunit.repository;

import org.springframework.data.repository.CrudRepository;

import lojaunit.entities.Venda;

public interface VendaRepository extends CrudRepository<Venda, Integer> {

}
