package br.com.jgb.api.entity.enums;

import java.util.Objects;

public enum TipoPessoaEnum {

    FISICA("F", "Física"),
    JURIDICA("J", "Jurídica");

    private String code;
    private String descricao;
    
    private TipoPessoaEnum(String code, String descricao) {
		this.code = code;
		this.descricao = descricao;
	}


	public static TipoPessoaEnum findByCode(String code) {
        if (Objects.isNull(code))
            return null;

        for (TipoPessoaEnum x : TipoPessoaEnum.values()) {
            if (code.equals(x.getCode()))
                return x;
        }

        throw new IllegalArgumentException("Invalid code: " + code);
    }


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    

}
