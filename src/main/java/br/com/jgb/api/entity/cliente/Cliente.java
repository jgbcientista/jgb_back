package br.com.jgb.api.entity.cliente;

import java.util.Objects;

import org.hibernate.Hibernate;

import br.com.jgb.api.entity.rh.pessoa.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Cliente.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente extends BaseEntity{
	public static final String TABLE_NAME = "TB_CLIENTE";

    @Column(name = "ID_PESSOA")
    @Schema(description = "Informacao da pessoa")
    @NotBlank
    private Long idPessoa;
    
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
