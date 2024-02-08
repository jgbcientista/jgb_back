package br.com.jgb.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jgb.api.entity.agenda.Agenda;
import br.com.jgb.api.mapper.AgendaMapper;
import br.com.jgb.api.request.AgendaRequest;
import br.com.jgb.api.response.AgendaResponse;
import br.com.jgb.api.service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/agenda")
@RequiredArgsConstructor // criar injeção de dependencia
public class AgendaController {

	private final AgendaService agendaService;
	private final AgendaMapper agendaMapper;

	@GetMapping
	@Operation(summary = "Obtem todos os eventos da base de dados.", tags = "Agenda")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
			@ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
			@ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
			@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<List<AgendaResponse>> buscarTodos() {
		List<Agenda> agendaList = agendaService.findAll();
		List<AgendaResponse> agendaResponses = agendaMapper.toEventoResponseList(agendaList);

		return ResponseEntity.status(HttpStatus.OK).body(agendaResponses);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtem o evento baseado no id da base de dados.", tags = "Agenda")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
			@ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
			@ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
			@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<AgendaResponse> buscarPorId(@PathVariable Long id) {

		Optional<Agenda> optionalAgenda = agendaService.buscarPorId(id);
		if (optionalAgenda.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		AgendaResponse agendaResponse = agendaMapper.toAgendaResponse(optionalAgenda.get());

		return ResponseEntity.status(HttpStatus.OK).body(agendaResponse);
	}

	@PostMapping
	@Operation(summary = "Salvar uma agenda na base de dados.", tags = "Agenda")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
			@ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
			@ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
			@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<AgendaResponse> salvar(@Valid @RequestBody AgendaRequest agendaRequest) {

		Agenda agenda = agendaMapper.toAgenda(agendaRequest);
		Agenda agendaSalva = agendaService.salvar(agenda);
		AgendaResponse agendaResponse = agendaMapper.toAgendaResponse(agendaSalva);

		return ResponseEntity.status(HttpStatus.CREATED).body(agendaResponse);
	}

}
