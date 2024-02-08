package br.com.jgb.api.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaFilter {
	private String nome;
	private String cpf;
	private Integer codigo;
}
