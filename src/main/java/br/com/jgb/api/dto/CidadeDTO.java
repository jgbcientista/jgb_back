package br.com.jgb.api.dto;

public class CidadeDTO {

	private Long id;

	private String nome;

	private Long uf;

	private Long ibge;

	private Double latitude;

	private Double longitude;

	private Long codTom;

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

	public Long getUf() {
		return uf;
	}

	public void setUf(Long uf) {
		this.uf = uf;
	}

	public Long getIbge() {
		return ibge;
	}

	public void setIbge(Long ibge) {
		this.ibge = ibge;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Long getCodTom() {
		return codTom;
	}

	public void setCodTom(Long codTom) {
		this.codTom = codTom;
	}

}
