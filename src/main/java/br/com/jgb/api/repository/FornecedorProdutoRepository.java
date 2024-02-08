package br.com.jgb.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.entity.fornecedor.FornecedorProduto;

@Repository
public interface FornecedorProdutoRepository extends JpaRepository<FornecedorProduto, Long> {
	
	@Query(value = "SELECT PRODUTO_ID FROM TB_PRODUTO_FORNECEDOR t WHERE  t.fornecedor_id = :idFornecedor ", nativeQuery = true)
	List<Long> findByIdFornecedor(@Param("idFornecedor") Long idFornecedor);
	
	@Query(value = "SELECT * FROM TB_PRODUTO_FORNECEDOR t WHERE  t.fornecedor_id = :idFornecedor and t.produto_id = :idProduto", nativeQuery = true)
	List<FornecedorProduto> findByIdFornecedorAndIdProduto(@Param("idFornecedor") Long idFornecedor, @Param("idProduto") Long idProduto);
	
	@Query(value = "SELECT * FROM TB_PRODUTO_FORNECEDOR WHERE produto_id  = :idProduto and fornecedor_id = :idFornecedor", nativeQuery = true)
	FornecedorProduto obterAssociacaoComProduto(@Param("idProduto") Long idProduto, @Param("idFornecedor") Long idFornecedor);
	
	
	@Query(value = "SELECT FORNECEDOR_ID FROM TB_PRODUTO_FORNECEDOR t WHERE  t.produto_id = :idProduto", nativeQuery = true)
	List<Long> findByIdProduto(@Param("idProduto") Long idProduto);
}
