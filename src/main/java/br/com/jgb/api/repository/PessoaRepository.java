package br.com.jgb.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.entity.rh.pessoa.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	@Query(value = "SELECT * FROM TB_PESSOA p WHERE p.cpf = :cpf", nativeQuery = true)
	List<Pessoa> findFilterCpf(@Param("cpf") String cpf);
	
	@Query(value = "SELECT * FROM TB_PESSOA p WHERE p.id = :id", nativeQuery = true)
	Pessoa obterPessoaById(@Param("id") Long id);
	
	@Query(value = "SELECT * FROM TB_PESSOA t WHERE t.id = :idPessoa", nativeQuery = true)
	Pessoa findPessoaId(@Param("idPessoa") Long idPessoa);
	
	@Query(value = "SELECT * FROM TB_PESSOA P WHERE P.id = :codigo", nativeQuery = true)
	List<Pessoa> findFilterCodigo(@Param("codigo") Integer codigo);
	
	@Query(value = "SELECT * FROM TB_PESSOA P WHERE P.nome = :nome", nativeQuery = true)
	List<Pessoa> findFilterByNome(@Param("nome") String nome);
	
	@Query(value = "SELECT * FROM TB_PESSOA P WHERE P.nome = :nome and P.cpf = :cpf", nativeQuery = true)
	List<Pessoa> findFilterByNomeAndCpf(@Param("nome") String nome, @Param("cpf") String cpf);
	
	@Query(value = "SELECT * FROM TB_PESSOA P WHERE P.cpf = :cpf and P.id =:codigo", nativeQuery = true)
	List<Pessoa> findFilterByCpfAndCodigo(@Param("cpf") String cpf, @Param("codigo") Integer codigo);
	
	@Query(value = "SELECT * FROM TB_PESSOA P WHERE P.nome = :nome and P.cpf = :cpf and P.id =:codigo", nativeQuery = true)
	List<Pessoa> findFilterByNomeAndCpfAndCodigo(@Param("nome") String nome, @Param("cpf") String cpf, @Param("codigo") Integer codigo);
	
	@Query(value = "SELECT * FROM TB_PESSOA P WHERE P.nome = :nome and P.id =:codigo", nativeQuery = true)
	List<Pessoa> findFilterByNomeAndCodigo(@Param("nome") String nome, @Param("codigo") Integer codigo);
	
	@Query(value = "SELECT p.* FROM TB_PESSOA as p INNER JOIN TB_CLIENTE as c on c.id_pessoa = p.id ", nativeQuery = true)
	List<Pessoa> findAllByCliente();
	
	@Query(value = "SELECT p.* FROM TB_PESSOA as p INNER JOIN TB_FORNECEDOR as c on c.id_pessoa = p.id ", nativeQuery = true)
	List<Pessoa> findAllByFornecedor();
	
}
