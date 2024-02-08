package br.com.jgb.api.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jgb.api.entity.agenda.Evento;
import br.com.jgb.api.mapper.EventoMapper;
import br.com.jgb.api.request.EventoRequest;
import br.com.jgb.api.response.EventoResponse;
import br.com.jgb.api.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/evento")
@RequiredArgsConstructor //criar injeção de dependencia
public class EventoController {

    private final EventoService service;
    private final EventoMapper mapper;

    @PostMapping 
	@Operation(summary = "Salva um evento na base de dados.", tags = "Evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<EventoResponse> salvar(@Valid  @RequestBody EventoRequest eventoRequest) {

        Evento evento = mapper.toEvento(eventoRequest);
        Evento eventoSalvo = service.salvar(evento);
        EventoResponse eventoResponse = mapper.toEventoResponse(eventoSalvo);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventoResponse);
    }

    @GetMapping 
	@Operation(summary = "Listar todos os eventos da base de dados.", tags = "Evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<List<EventoResponse>> findAll() {
        List<Evento> eventos = service.findAll();
        List<EventoResponse> eventoResponses = mapper.toEventoResponseList(eventos);

        return ResponseEntity.status(HttpStatus.OK).body(eventoResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar evento por ID.", tags = "Evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<EventoResponse> buscarPorIdl(@PathVariable Long id) {

        Optional<Evento> optionalEvento = service.findById(id);

        if (optionalEvento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toEventoResponse(optionalEvento.get()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar evento por ID.", tags = "Evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<EventoResponse> alterar(@PathVariable Long id, @RequestBody EventoRequest eventoRequest) {
        Evento evento = mapper.toEvento(eventoRequest);
        Evento eventoSalvo1 = service.alterar(id, evento);
        EventoResponse eventoResponse = mapper.toEventoResponse(eventoSalvo1);

        return ResponseEntity.status(HttpStatus.OK).body(eventoResponse);

    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover evento da base de dados.", tags = "Evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}