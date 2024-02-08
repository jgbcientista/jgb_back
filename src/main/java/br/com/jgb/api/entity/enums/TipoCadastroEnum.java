package br.com.jgb.api.entity.enums;

import java.util.Objects;

public enum TipoCadastroEnum {

	CLIENTE(1, "ROLE_CLIENTE", "CLIENTE"),
    FORNECEDOR(2, "ROLE_FORNECEDOR", "FORNECEDOR"),
    FUNCIONARIO(3, "ROLE_FUNCIONARIO", "FUNCIONARIO"),
    ;

    private Integer code;
    private String sigla;
    private String descricao;
    
    private TipoCadastroEnum(Integer code, String descricao, String sigla) {
		this.code = code;
		this.sigla = sigla;
		this.descricao = descricao;
	}


	public static TipoCadastroEnum findByCode(Integer code) {
        if (Objects.isNull(code))
            return null;

        for (TipoCadastroEnum x : TipoCadastroEnum.values()) {
            if (code.equals(x.getCode()))
                return x;
        }

        throw new IllegalArgumentException("Invalid code: " + code);
    }


	public Integer getCode() {
		return code;
	}


	public void setCode(Integer code) {
		this.code = code;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getSigla() {
		return sigla;
	}


	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
    
	

}
