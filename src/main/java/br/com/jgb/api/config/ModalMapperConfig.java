package br.com.jgb.api.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //para o spring saber que é uma classe de configuração
public class ModalMapperConfig {

    //por que eu vou usar várias vezes, e quero que o spring faça essa injeção de dependencia automatica
    @Bean
    public ModelMapper modelMapper (){
        return new ModelMapper();
    }

}
