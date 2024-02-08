package br.com.jgb.api.entity.patrimonio;

import java.util.Objects;

import org.hibernate.Hibernate;

import br.com.jgb.api.entity.rh.pessoa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Patrimonio.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Patrimonio extends BaseEntity {
	public static final String TABLE_NAME = "TB_PATRIMONIO";
	
	/*
	 * Por definição, patrimônio é o conjunto de bens, direitos e obrigações de uma pessoa ou empresa.
	 */
	
    @Column(name = "DESCRICAO", length = 255, nullable = false)
    @Size(min = 1, max = 255)
    @NotBlank
    private String descricao;
    
    @Column(name = "CODIGO", length = 255, nullable = false)
    @Size(min = 1, max = 50)
    @NotBlank
    private String codigo;
    
    @Column(name = "TIPO_PATRIMONIO", length = 255, nullable = false)
    @Size(min = 1, max = 255)
    @NotBlank
    private String tipoPatrimonio;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntity that = (BaseEntity) o;
        return that.getId() != null && Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
}
