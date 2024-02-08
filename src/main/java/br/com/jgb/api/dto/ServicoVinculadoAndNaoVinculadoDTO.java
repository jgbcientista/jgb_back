package br.com.jgb.api.dto;

import java.util.List;

public class ServicoVinculadoAndNaoVinculadoDTO {
	
    private List<ServicoDTO> servicosContratados;
    private List<ServicoDTO> servicosNaoContratados;
    private List<ProdutoDTO> produtosContratados;
    private List<ProdutoDTO> produtosNaoContratados;
    
	private Integer code;
	private String message;
    
    
	public List<ServicoDTO> getServicosContratados() {
		return servicosContratados;
	}
	public void setServicosContratados(List<ServicoDTO> servicosContratados) {
		this.servicosContratados = servicosContratados;
	}
	public List<ServicoDTO> getServicosNaoContratados() {
		return servicosNaoContratados;
	}
	public void setServicosNaoContratados(List<ServicoDTO> servicosNaoContratados) {
		this.servicosNaoContratados = servicosNaoContratados;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<ProdutoDTO> getProdutosContratados() {
		return produtosContratados;
	}
	public void setProdutosContratados(List<ProdutoDTO> produtosContratados) {
		this.produtosContratados = produtosContratados;
	}
	public List<ProdutoDTO> getProdutosNaoContratados() {
		return produtosNaoContratados;
	}
	public void setProdutosNaoContratados(List<ProdutoDTO> produtosNaoContratados) {
		this.produtosNaoContratados = produtosNaoContratados;
	}
	
	

}
