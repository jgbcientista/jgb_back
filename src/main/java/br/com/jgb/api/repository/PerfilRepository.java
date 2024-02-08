package br.com.jgb.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.entity.rh.pessoa.usuario.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
	
	@Query(value = "SELECT * FROM TB_PERFIL p WHERE p.id = :id", nativeQuery = true)
	Perfil findPorId(@Param("id") Long id);
	
	@Query(value = "SELECT * FROM TB_PERFIL p WHERE p.nome = :nome", nativeQuery = true)
	Perfil findByNome(@Param("nome") String nome);
	
	@Query(value = "SELECT * FROM TB_PERFIL p WHERE p.descricao = :descricao", nativeQuery = true)
	List<Perfil> findFilterByDescricao(@Param("descricao") String descricao);
	
	@Query(value = "SELECT * FROM TB_PERFIL p WHERE p.id = :codigo", nativeQuery = true)
	List<Perfil> findFilterByCodigo(@Param("codigo") Integer codigo);
	
	@Query(value = "SELECT * FROM TB_PERFIL p WHERE  p.id = :codigo and p.descricao = :descricao", nativeQuery = true)
	List<Perfil> findFilterByCodigoAndDescricao(@Param("codigo") Integer codigo, @Param("descricao") String descricao);

}
