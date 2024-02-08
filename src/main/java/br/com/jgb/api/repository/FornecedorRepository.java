package br.com.jgb.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.entity.fornecedor.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

	@Query(value = "SELECT * FROM TB_FORNECEDOR t WHERE t.id = :idFornecedor ", nativeQuery = true)
	Fornecedor findByPorId(@Param("idFornecedor") Long idFornecedor);
	
	@Query(value = "SELECT * FROM TB_FORNECEDOR t WHERE t.id_pessoa = :idPessoa ", nativeQuery = true)
	Fornecedor findFornecedorByPessoaId(@Param("idPessoa") Long idPessoa);
	
	@Query(value = "SELECT c.* FROM TB_FORNECEDOR c INNER JOIN TB_PESSOA p ON p.id = c.ID_PESSOA ", nativeQuery = true)
	List<Fornecedor> all();
	
	@Query(value = "SELECT c.* FROM TB_PESSOA p INNER JOIN TB_FORNECEDOR c on c.id_pessoa = p.id WHERE p.cpf = :cpf", nativeQuery = true)
	Fornecedor findByCPF(@Param("cpf") String cpf);
	
	@Query(value = "SELECT * FROM TB_FORNECEDOR t WHERE  t.id in (:ids)", nativeQuery = true)
	List<Fornecedor> findFilterByIdIn(@Param("ids") List<Long> ids);
	
	@Query(value = "SELECT * FROM TB_FORNECEDOR t WHERE  t.id not in (:ids)", nativeQuery = true)
	List<Fornecedor> findFilterByIdNotIn(@Param("ids") List<Long> ids);
}
