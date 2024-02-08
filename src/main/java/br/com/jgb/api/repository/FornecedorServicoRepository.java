package br.com.jgb.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.entity.fornecedor.FornecedorServico;

@Repository
public interface FornecedorServicoRepository extends JpaRepository<FornecedorServico, Long> {
	
	@Query(value = "SELECT SERVICO_ID FROM TB_SERVICO_FORNECEDOR t WHERE t.fornecedor_id = :idFornecedor ", nativeQuery = true)
	List<Long> findByIdFornecedor(@Param("idFornecedor") Long idFornecedor);
	
	@Query(value = "SELECT * FROM TB_SERVICO_FORNECEDOR t WHERE  t.fornecedor_id = :idFornecedor and t.servico_id = :idServico", nativeQuery = true)
	List<FornecedorServico> findByIdFornecedorAndIdServico(@Param("idFornecedor") Long idFornecedor, @Param("idServico") Long idServico);

	@Query(value = "SELECT * FROM TB_SERVICO_FORNECEDOR WHERE servico_id  = :idServico and fornecedor_id = :idFornecedor", nativeQuery = true)
	FornecedorServico obterAssociacaoComServico(@Param("idServico") Long idServico, @Param("idFornecedor") Long idFornecedor);
	
	@Query(value = "SELECT FORNECEDOR_ID FROM TB_SERVICO_FORNECEDOR t WHERE t.servico_id = :idServico", nativeQuery = true)
	List<Long> findByIdServico(@Param("idServico") Long idServico);
}
