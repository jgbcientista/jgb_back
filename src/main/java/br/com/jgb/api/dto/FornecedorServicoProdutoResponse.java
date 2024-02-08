package br.com.jgb.api.dto;

import java.util.List;

public class FornecedorServicoProdutoResponse {
 
	private Long idServico;
	private List<FornecedorDTO> fornecedoresServicos;
	private Long idProduto;
	private List<FornecedorDTO> fornecedoresProdutos;
	
	public Long getIdServico() {
		return idServico;
	}
	public void setIdServico(Long idServico) {
		this.idServico = idServico;
	}
	public List<FornecedorDTO> getFornecedoresServicos() {
		return fornecedoresServicos;
	}
	public void setFornecedoresServicos(List<FornecedorDTO> fornecedoresServicos) {
		this.fornecedoresServicos = fornecedoresServicos;
	}
	public Long getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}
	public List<FornecedorDTO> getFornecedoresProdutos() {
		return fornecedoresProdutos;
	}
	public void setFornecedoresProdutos(List<FornecedorDTO> fornecedoresProdutos) {
		this.fornecedoresProdutos = fornecedoresProdutos;
	}

	
	
	 
	

}
