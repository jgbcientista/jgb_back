package br.com.jgb.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.entity.fornecedor.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	
	@Query(value = "SELECT * FROM TB_PRODUTO t WHERE t.id = :idProduto", nativeQuery = true)
	Produto findByProdutoId(@Param("idProduto") Long idProduto);
	
	@Query(value = "SELECT * FROM TB_PRODUTO p WHERE p.descricao = :descricao", nativeQuery = true)
	List<Produto> findFilterByDescricao(@Param("descricao") String descricao);
	
	@Query(value = "SELECT * FROM TB_PRODUTO p WHERE p.id = :codigo", nativeQuery = true)
	List<Produto> findFilterByCodigo(@Param("codigo") Integer codigo);
	
	@Query(value = "SELECT * FROM TB_PRODUTO p WHERE  p.id = :codigo and p.descricao = :descricao", nativeQuery = true)
	List<Produto> findFilterByCodigoAndDescricao(@Param("codigo") Integer codigo, @Param("descricao") String descricao);
	
	@Query(value = "SELECT * FROM TB_PRODUTO t WHERE  t.id in (:ids)", nativeQuery = true)
	List<Produto> findFilterByIdIn(@Param("ids") List<Long> ids);
	
	@Query(value = "SELECT * FROM TB_PRODUTO t WHERE  t.id in (:ids)", nativeQuery = true)
	List<Produto> findContratados(@Param("ids") List<Long> ids);
	
	@Query(value = "SELECT * FROM TB_PRODUTO t WHERE  t.id not in (:ids)", nativeQuery = true)
	List<Produto> findNaoContratados(@Param("ids") List<Long> ids);
}
