package br.com.jgb.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.entity.patrimonio.Patrimonio;

@Repository
public interface PatrimonioRepository extends JpaRepository<Patrimonio, Long> {

	@Query(value = "SELECT * FROM TB_PATRIMONIO p WHERE p.id = :id", nativeQuery = true)
	List<Patrimonio> findFilterId(@Param("id") String id);
	
	@Query(value = "SELECT * FROM TB_PATRIMONIO p WHERE p.codigo = :codigo", nativeQuery = true)
	List<Patrimonio> findFilterCodigo(@Param("codigo") String codigo);
	
	@Query(value = "SELECT * FROM TB_PATRIMONIO p WHERE p.descricao like %:descricao%", nativeQuery = true)
	List<Patrimonio> findFilterByDescricao(@Param("descricao") String descricao);
	
	@Query(value = "SELECT * FROM TB_PATRIMONIO p WHERE p.tipo_patrimonio = :tipoPatrimonio", nativeQuery = true)
	List<Patrimonio> findFilterTipoPatrimonio(@Param("tipoPatrimonio") String tipoPatrimonio);
	
	@Query(value = "SELECT * FROM TB_PATRIMONIO p WHERE p.descricao = :descricao and p.tipo_patrimonio = :tipoPatrimonio", nativeQuery = true)
	List<Patrimonio> findFilterByDescricaoAndTipoPatromonio(@Param("descricao") String descricao, @Param("tipoPatrimonio") String tipoPatrimonio);
	
	@Query(value = "SELECT * FROM TB_PATRIMONIO p WHERE p.tipo_patrimonio = :tipoPatrimonio and p.codigo =:codigo", nativeQuery = true)
	List<Patrimonio> findFilterByTipoPatrimonioAndCodigo(@Param("tipoPatrimonio") String tipoPatrimonio, @Param("codigo") String codigo);
	
	@Query(value = "SELECT * FROM TB_PATRIMONIO p WHERE p.descricao = :descricao and p.tipo_patrimonio = :tipoPatrimonio and p.codigo =:codigo", nativeQuery = true)
	List<Patrimonio> findFilterByDescricaoAndTipoPatrimonioAndCodigo(@Param("descricao") String descricao, @Param("tipoPatrimonio") String tipoPatrimonio, @Param("codigo") String codigo);
	
	@Query(value = "SELECT * FROM TB_PATRIMONIO p WHERE p.descricao = :descricao and p.codigo =:codigo", nativeQuery = true)
	List<Patrimonio> findFilterByDescricaoAndCodigo(@Param("descricao") String descricao, @Param("codigo") String codigo);
	
}
