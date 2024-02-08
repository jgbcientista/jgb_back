package br.com.jgb.api.entity.agenda;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.Hibernate;

import br.com.jgb.api.entity.rh.pessoa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Agenda.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Agenda {
	
	public static final String TABLE_NAME = "TB_AGENDA";

	@Id
	@Column(name = "ID", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "DATA_HORA")//esse padrão é o da tabela()
    private LocalDateTime horario;

    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;

    @ManyToOne//para cada vez que eu registro uma agenda nós temos um evento
    private Evento evento;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		BaseEntity that = (BaseEntity) o;
		return that.getId() != null && Objects.equals(this.getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
