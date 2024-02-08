package br.com.jgb.api.entity.rh.funcionario;

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
@Table(name = Funcao.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Funcao {
	public static final String TABLE_NAME = "TB_FUNCAO";

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "DESCRICAO", nullable = false)
	private String descricao;
	
	@Column(name = "SITUACAO", nullable = false)
	private Boolean situacao;

}
