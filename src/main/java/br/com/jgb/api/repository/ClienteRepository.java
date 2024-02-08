package br.com.jgb.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.entity.cliente.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query(value = "SELECT * FROM TB_CLIENTE t WHERE t.id_pessoa = :idPessoa ", nativeQuery = true)
	Cliente findClienteByPessoaId(@Param("idPessoa") Long idPessoa);
	
	@Query(value = "SELECT c.* FROM TB_CLIENTE c INNER JOIN TB_PESSOA p ON p.id = c.ID_PESSOA ", nativeQuery = true)
	List<Cliente> all();
	
	@Query(value = "SELECT c.* FROM TB_PESSOA p INNER JOIN TB_CLIENTE c on c.id_pessoa = p.id WHERE p.cpf = :cpf", nativeQuery = true)
	Cliente findByCPF(@Param("cpf") String cpf);
	
	@Query(value = "SELECT * FROM TB_CLIENTE t WHERE  t.id in (:ids)", nativeQuery = true)
	List<Cliente> findFilterByIdIn(@Param("ids") List<Long> ids);
}
