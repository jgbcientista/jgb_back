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
@Table(name = Servico.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class Servico extends BaseEntity implements Serializable {
	/**
	 * Servico
	 */
	private static final long serialVersionUID = 4961068842635944027L;

	public static final String TABLE_NAME = "TB_SERVICO";
	
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
