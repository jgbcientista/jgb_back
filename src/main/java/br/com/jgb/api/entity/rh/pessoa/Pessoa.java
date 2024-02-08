package br.com.jgb.api.entity.rh.pessoa;

import java.time.LocalDate;
import java.util.Objects;

import org.hibernate.Hibernate;

import br.com.jgb.api.entity.rh.pessoa.usuario.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = Pessoa.TABLE_NAME)
@Data
public class Pessoa extends BaseEntity {
	public static final String TABLE_NAME = "TB_PESSOA";

    @Column(name = "nome", length = 255, nullable = false)
    @Size(min = 1, max = 255)
    @NotBlank
    private String nome;
    
    @Column(name = "sexo", length = 10, nullable = false)
    @Size(min = 1, max = 10)
    @NotBlank
    private String sexo;
    
    @Column(name = "cpf", length = 255, nullable = false)
    @Size(min = 1, max = 11)
    @NotBlank
    private String cpf;
    
    @Column(name = "rg", length = 255, nullable = false)
    @Size(min = 1, max = 50)
    @NotBlank
    private String rg;
    
    @Column(name = "ctps", length = 255, nullable = false)
    @Size(min = 1, max = 50)
    @NotBlank
    private String ctps;
    
    @Column(name = "email", length = 255, nullable = false)
    @Size(min = 1, max = 100)
    @NotBlank
    private String email;
    
    @Column(name = "telefone", length = 255, nullable = false)
    @Size(min = 1, max = 50)
    @NotBlank
    private String telefone;
    
    @Column(name = "DATA_NASCIMENTO", nullable = false)
    private LocalDate dataNascimento;
    
    @Column(name = "TIPO_PESSOA",  nullable = false)
    private String tipoPessoa;
    
    @OneToOne
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID")
    @Schema(description = "Proprietário do token")
    private Usuario usuario;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntity that = (BaseEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    

}
