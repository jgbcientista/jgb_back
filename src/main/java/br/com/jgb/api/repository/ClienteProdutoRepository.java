package br.com.jgb.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.entity.cliente.ClienteProduto;

@Repository
public interface ClienteProdutoRepository extends JpaRepository<ClienteProduto, Long> {

	@Query(value = "SELECT t.produto_id FROM TB_PRODUTO_CLIENTE t WHERE  t.cliente_id = :idCliente ", nativeQuery = true)
	public List<Long> findByIdCliente(@Param("idCliente") Long idCliente);
	
	@Query(value = "SELECT * FROM TB_PRODUTO_CLIENTE t WHERE  t.cliente_id = :idCliente and t.produto_id = :idProduto", nativeQuery = true)
	List<ClienteProduto> findByIdClienteAndIdProduto(@Param("idCliente") Long idCliente, @Param("idProduto") Long idProduto);
	
	@Query(value = "delete FROM TB_PRODUTO_CLIENTE WHERE produto_id  = :idProduto and cliente_id = :idCliente", nativeQuery = true)
	void removerAssociacaoComProduto(@Param("idProduto") Long idProduto, @Param("idCliente") Long idCliente);
	
	@Query(value = "SELECT CLIENTE_ID FROM TB_PRODUTO_CLIENTE t WHERE  t.produto_id = :idProduto", nativeQuery = true)
	List<Long> findByIdProduto(@Param("idProduto") Long idProduto);

}
