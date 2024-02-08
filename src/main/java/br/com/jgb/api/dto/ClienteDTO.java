package br.com.jgb.api.dto;

import java.util.List;

public class ClienteDTO {

	private Long id;
	private String cpf;
	private String nome;
	private List<Long> servicosSelecionados;
	private List<Long> produtosSelecionados;
	private Boolean situacao;
	private Integer code;
	private String message;
	private List<ServicoDTO> servicos;
	private List<ProdutoDTO> produtos;
	private PessoaDTO pessoa;
	private Long IdPessoa;

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	public List<Long> getServicosSelecionados() {
		return servicosSelecionados;
	}

	public void setServicosSelecionados(List<Long> servicosSelecionados) {
		this.servicosSelecionados = servicosSelecionados;
	}

	public List<Long> getProdutosSelecionados() {
		return produtosSelecionados;
	}

	public void setProdutosSelecionados(List<Long> produtosSelecionados) {
		this.produtosSelecionados = produtosSelecionados;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<ServicoDTO> getServicos() {
		return servicos;
	}

	public void setServicos(List<ServicoDTO> servicos) {
		this.servicos = servicos;
	}

	public List<ProdutoDTO> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoDTO> produtos) {
		this.produtos = produtos;
	}

	public PessoaDTO getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaDTO pessoa) {
		this.pessoa = pessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getIdPessoa() {
		return IdPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		IdPessoa = idPessoa;
	}
	

}
