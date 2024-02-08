package br.com.jgb.api.entity.agenda;

import java.util.Objects;

import org.hibernate.Hibernate;

import br.com.jgb.api.entity.rh.pessoa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Evento.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Evento {
	
	public static final String TABLE_NAME = "TB_EVENTO";

	@Id
	@Column(name = "ID", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
   
	@Column(name = "NOME_EVENTO")
	private String nomeEvento;
	
	@Column(name = "NOME_RESPONSAVEL")
	private String nomeResponsavel;
	
	@Column(name = "EMAIL")
	private String email;
    
	@Column(name = "CPF")
	private String cpf;
    
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
