package lojaunit.repository;

import org.springframework.data.repository.CrudRepository;

import lojaunit.entities.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

}
