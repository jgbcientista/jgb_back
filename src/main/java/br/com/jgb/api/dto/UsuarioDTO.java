package br.com.jgb.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDTO {

    @NotBlank
    @Size(min = 4, max = 60)
    private String login;
    
    @NotBlank
    @Size(min = 6, max = 60)
    private String senha;

}
