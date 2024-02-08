package br.com.jgb.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.entity.cidade.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
	@Query(value = "SELECT * FROM TB_CIDADE c WHERE c.uf = :uf", nativeQuery = true)
	List<Cidade> findByIdEstado(@Param("uf") Long uf);

}
