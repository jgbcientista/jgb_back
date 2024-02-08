package br.com.jgb.api.dto;

import br.com.jgb.api.entity.cliente.Cliente;
import br.com.jgb.api.entity.fornecedor.Produto;

public class ClienteProdutoDTO {

	private Long id;
	private Produto produto;
	private Cliente cliente;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	 
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
 

}
