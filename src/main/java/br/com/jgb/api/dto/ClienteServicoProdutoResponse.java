package br.com.jgb.api.dto;

import java.util.List;

public class ClienteServicoProdutoResponse {
 
	private Long idServico;
	private List<ClienteDTO> clientesServicos;
	private Long idProduto;
	private List<ClienteDTO> clientesProdutos;
	public Long getIdServico() {
		return idServico;
	}
	public void setIdServico(Long idServico) {
		this.idServico = idServico;
	}
	public List<ClienteDTO> getClientesServicos() {
		return clientesServicos;
	}
	public void setClientesServicos(List<ClienteDTO> clientesServicos) {
		this.clientesServicos = clientesServicos;
	}
	public Long getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}
	public List<ClienteDTO> getClientesProdutos() {
		return clientesProdutos;
	}
	public void setClientesProdutos(List<ClienteDTO> clientesProdutos) {
		this.clientesProdutos = clientesProdutos;
	}
 
	 
	
	
	 
	

}
