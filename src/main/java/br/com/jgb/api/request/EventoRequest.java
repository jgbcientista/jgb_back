package br.com.jgb.api.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//essa classe seria o mesmo que o DTO = request - solicitação
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoRequest {
   
	@NotBlank(message = "Nome do evento é obrigatório")
    private String nomeEvento;

    @NotBlank(message = "Nome do responsável é obrigatorio")
    private String nomeResponsavel;

    private String email;

    @NotBlank(message = "CPF é obrigatório")
    private String cpf;
}
