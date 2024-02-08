package br.com.jgb.api.entity.cidade;

import java.util.Objects;

import org.hibernate.Hibernate;

import br.com.jgb.api.entity.rh.pessoa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Cidade.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cidade {
	public static final String TABLE_NAME = "TB_CIDADE";

	@Id
	@Column(name = "ID", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NOME", length = 100, nullable = false, unique = true)
	@Size(min = 2, max = 255)
	private String nome;
	
	@Column(name = "UF", nullable = false, unique = true)
	private Long uf;
	
	@Column(name = "IBGE", nullable = false, unique = true)
	private Long ibge;
	
	@Column(name = "LATITUDE", nullable = false, unique = true)
	private Double latitude;
	
	@Column(name = "LONGITUDE", nullable = false, unique = true)
	private Double longitude;
	
	@Column(name = "COD_TOM", nullable = false, unique = true)
	private Long codTom;
	
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
