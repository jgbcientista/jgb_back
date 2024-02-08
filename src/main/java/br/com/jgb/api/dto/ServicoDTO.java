package br.com.jgb.api.dto;

import java.util.List;

public class ServicoDTO {
	
	private Long id;
    private String descricao;
    private String tipoPatrimonio;
	private Integer code;
	private String message;
	private Long idPessoa;
	private Long idCliente;
	private List<Long> idsFornecedores;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	public String getTipoPatrimonio() {
		return tipoPatrimonio;
	}
	public void setTipoPatrimonio(String tipoPatrimonio) {
		this.tipoPatrimonio = tipoPatrimonio;
	}
	public Long getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public List<Long> getIdsFornecedores() {
		return idsFornecedores;
	}
	public void setIdsFornecedores(List<Long> idsFornecedores) {
		this.idsFornecedores = idsFornecedores;
	}

}
