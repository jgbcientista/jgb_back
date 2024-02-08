package br.com.jgb.api.dto;

public class EstadoDTO {

	private Long id;
	private String nome;
	private String uf;
	private Long ibge;
	private Long pais;
	private String ddd;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public Long getIbge() {
		return ibge;
	}
	public void setIbge(Long ibge) {
		this.ibge = ibge;
	}
	public Long getPais() {
		return pais;
	}
	public void setPais(Long pais) {
		this.pais = pais;
	}
	public String getDdd() {
		return ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	
	

}
