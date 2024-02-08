package br.com.jgb.api.entity.rh.funcionario;

import br.com.jgb.api.entity.rh.pessoa.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Tarefa.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tarefa {
    public static final String TABLE_NAME = "TB_TAREFA";

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID", nullable = false, updatable = false)
    private Usuario usuario;

    @Column(name = "descricao", length = 255, nullable = false)
    @Size(min = 1, max = 255)
    @NotBlank
    private String descricao;

}
