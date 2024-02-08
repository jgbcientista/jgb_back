package br.com.jgb.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.entity.cliente.ClienteServico;

@Repository
public interface ClienteServicoRepository extends JpaRepository<ClienteServico, Long> {
	
	@Query(value = "SELECT SERVICO_ID FROM TB_SERVICO_CLIENTE t WHERE  t.cliente_id = :idCliente ", nativeQuery = true)
	List<Long> findByIdCliente(@Param("idCliente") Long idCliente);
	
	@Query(value = "SELECT * FROM TB_SERVICO_CLIENTE t WHERE  t.cliente_id = :idCliente and t.servico_id = :idServico", nativeQuery = true)
	List<ClienteServico> findByIdClienteAndIdServico(@Param("idCliente") Long idCliente, @Param("idServico") Long idServico);

	@Query(value = "SELECT * FROM tb_servico_cliente WHERE servico_id  = :idServico and cliente_id = :idCliente", nativeQuery = true)
	ClienteServico obterAssociacaoComServico(@Param("idServico") Long idServico, @Param("idCliente") Long idCliente);
	
	@Query(value = "SELECT CLIENTE_ID FROM TB_SERVICO_CLIENTE t WHERE t.servico_id = :idServico", nativeQuery = true)
	List<Long> findByIdServico(@Param("idServico") Long idServico);
}
