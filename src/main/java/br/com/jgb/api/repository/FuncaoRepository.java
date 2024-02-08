package br.com.jgb.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.entity.rh.funcionario.Funcao;

@Repository
public interface FuncaoRepository extends JpaRepository<Funcao, Long> {
	
	@Query(value = "SELECT * FROM TB_FUNCAO p WHERE p.descricao = :descricao", nativeQuery = true)
	List<Funcao> findFilterByDescricao(@Param("descricao") String descricao);
	
	@Query(value = "SELECT * FROM TB_FUNCAO p WHERE p.id = :codigo", nativeQuery = true)
	List<Funcao> findFilterByCodigo(@Param("codigo") Integer codigo);
	
	@Query(value = "SELECT * FROM TB_FUNCAO p WHERE  p.id = :codigo and p.descricao = :descricao", nativeQuery = true)
	List<Funcao> findFilterByCodigoAndDescricao(@Param("codigo") Integer codigo, @Param("descricao") String descricao);

}
