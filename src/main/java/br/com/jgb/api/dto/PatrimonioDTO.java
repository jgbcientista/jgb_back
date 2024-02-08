package br.com.jgb.api.dto;

public class PatrimonioDTO {
	
	private Long id;
    private String descricao;
    private String codigo;
	private String tipoPatrimonio;
	private Integer code;
	private String message;
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
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getTipoPatrimonio() {
		return tipoPatrimonio;
	}
	public void setTipoPatrimonio(String tipoPatrimonio) {
		this.tipoPatrimonio = tipoPatrimonio;
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

    
	 
	  

}
