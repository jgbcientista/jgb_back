package br.com.jgb.api.entity.fornecedor;

import java.io.Serializable;

import br.com.jgb.api.entity.rh.pessoa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Produto.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class Produto extends BaseEntity implements Serializable {
	/**
	 * Produto
	 */
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "TB_PRODUTO";
	
    @Column(name = "DESCRICAO", length = 100, nullable = false, unique = true)
    @Size(min = 2, max = 255)
    @NotBlank
    private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
   
    

}
