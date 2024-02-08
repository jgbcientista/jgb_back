package br.com.jgb.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.entity.fornecedor.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
	
	
	@Query(value = "SELECT * FROM TB_SERVICO t WHERE t.id = :id", nativeQuery = true)
	Servico findByServicoId(@Param("id") Long id);
	
	@Query(value = "SELECT * FROM TB_SERVICO p WHERE p.descricao = :descricao", nativeQuery = true)
	List<Servico> findFilterByDescricao(@Param("descricao") String descricao);
	
	@Query(value = "SELECT * FROM TB_SERVICO p WHERE p.id = :codigo", nativeQuery = true)
	List<Servico> findFilterByCodigo(@Param("codigo") Integer codigo);
	
	@Query(value = "SELECT * FROM TB_SERVICO p WHERE  p.id = :codigo and p.descricao = :descricao", nativeQuery = true)
	List<Servico> findFilterByCodigoAndDescricao(@Param("codigo") Integer codigo, @Param("descricao") String descricao);
	
	@Query(value = "SELECT * FROM TB_SERVICO t WHERE  t.id in (:ids)", nativeQuery = true)
	List<Servico> findFilterByIdIn(@Param("ids") List<Long> ids);
	
	@Query(value = "SELECT * FROM TB_SERVICO t WHERE t.id in (:ids)", nativeQuery = true)
	List<Servico> findContratados(@Param("ids") List<Long> ids);
	
	@Query(value = "SELECT * FROM TB_SERVICO t WHERE  t.id not in (:ids)", nativeQuery = true)
	List<Servico> findNaoContratados(@Param("ids") List<Long> ids);
	
	
}
