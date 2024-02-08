package br.com.jgb.api.entity.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public enum TipoPatrimonioEnum {

    TERREMOS_CONSTRUCOES(1L, "Terrenos e construções"),
    VEICULOS(2L, "Veículos"),
    MAQUIANS_EQUIPAMENTOS(3L, "Máquinas e equipamentos"),
    MOVEIS_UTENSILIOS(4L, "Móveis e utensílios"),
    ESTOQUE(5L, "Estoque"),
    MARCAS_PATENTES(6L, "Marcas e patentes e etc.");
	
    private Long code;
    private String descricao;

    private TipoPatrimonioEnum(Long code, String descricao) {
		this.code = code;
		this.descricao = descricao;
	}
    
    public static List<String> findTiposPatrimonios() {
        List<String> patrimonios = new ArrayList<>();

        for (TipoPatrimonioEnum tipoPatrimonio : values())
            patrimonios.add(tipoPatrimonio.getDescricao());

        return patrimonios;
    }


	public static TipoPatrimonioEnum toEnum(Long id) {
        if (Objects.isNull(id))
            return null;

        for (TipoPatrimonioEnum x : TipoPatrimonioEnum.values()) {
            if (id.equals(x.getCode()))
                return x;
        }
        throw new IllegalArgumentException("Código inválido: " + id);
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
