package br.com.jgb.api.dto;

import br.com.jgb.api.entity.cliente.Cliente;
import br.com.jgb.api.entity.fornecedor.Servico;

public class ClienteServicoDTO {

	private Long id;
	private Servico servico;
	private Cliente cliente;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Servico getServico() {
		return servico;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
 

}
