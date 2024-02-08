package br.com.jgb.api.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//essa classe seria o mesmo que o DTO - Response
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoResponse {

    private Long id;
    
    private String nomeEvento;
    
    private String nomeResponsavel;
    
    private String email;
    
    private String cpf;
}
