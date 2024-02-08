package br.com.jgb.api.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatrimonioFilter {
	private String codigo;
	private String descricao;
	private String tipoPatrimonio;
	private String marca;
	
}
