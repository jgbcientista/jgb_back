package br.com.jgb.api.entity.rh.funcionario;

import java.util.Objects;

import org.hibernate.Hibernate;

import br.com.jgb.api.entity.rh.pessoa.BaseEntity;
import br.com.jgb.api.entity.rh.pessoa.Pessoa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Funcionario.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Funcionario extends BaseEntity{
	public static final String TABLE_NAME = "TB_FUNCIONARIO";
	
    @OneToOne
    @JoinColumn(name = "ID_FUNCAO", referencedColumnName = "id")
    @Schema(description = "Funcao do funcionario")
    private Funcao funcao;
    
    @OneToOne
    @JoinColumn(name = "ID_PESSOA", referencedColumnName = "id")
    @Schema(description = "Funcao da pessoa")
    private Pessoa pessoa;
    
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
