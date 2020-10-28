package lojaunit.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import lojaunit.entities.ItensVenda;

public interface ItensVendaRepository extends CrudRepository<ItensVenda, Integer>{

	@Query(value = "select * from itens_venda where id_venda= ?1 and id_produto=?2", nativeQuery = true)
	Optional<ItensVenda> findByIdVendaAndIdProduto(Integer idVenda, Integer idProduto);

	@Transactional
	@Modifying
	@Query(value = "delete from itens_venda where id_venda= ?1 and id_produto=?2", nativeQuery = true)
	void deleteByIds(Integer idVenda, Integer idProduto);
}
