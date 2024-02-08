package br.com.jgb.api.entity.enums;

import java.util.Objects;

public enum PerfilEnum {

    
    CLIENTE(1L, "ROLE_CLIENTE"),
    FUNCIONARIO(2L, "ROLE_FUNCIONARIO"),
    FORNECEDOR(3L, "ROLE_FORNECEDOR"),
    USUARIO_COMUM(3L, "ROLE_USUARIO_COMUM"),
    USUARIO_ADMIN(3L, "ROLE_USUARIO_ADMINISTRADOR");

    private Long code;
    private String descricao;

    private PerfilEnum(Long code, String descricao) {
		this.code = code;
		this.descricao = descricao;
	}


	public static PerfilEnum toEnum(Long id) {
        if (Objects.isNull(id))
            return null;

        for (PerfilEnum x : PerfilEnum.values()) {
            if (id.equals(x.getCode()))
                return x;
        }

        throw new IllegalArgumentException("Invalid code: " + id);
    }
    

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    

}
