package br.com.jgb.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jgb.api.dto.CidadeDTO;
import br.com.jgb.api.dto.EstadoDTO;
import br.com.jgb.api.service.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/cidade")
@Validated
public class CidadeController {

	@Autowired
	private CidadeService service;

	@GetMapping("/{id}")
	@Operation(summary = "Obter dados da cidade pelo.", tags = "Cidade")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Bem-sucedida"),
		@ApiResponse(responseCode = "401", description = "Quando o não for autorizado"),
		@ApiResponse(responseCode = "403", description = "Quando for proibido o acesso"),
		@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<CidadeDTO> findById(@PathVariable Long id) {
		CidadeDTO obj = this.service.findById(id);
		return ResponseEntity.ok(obj);
	}

	@GetMapping("/cidades")
	@Operation(summary = "Obter Todas as cidades do Brasil.", tags = "Cidade")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Bem-sucedida"),
		@ApiResponse(responseCode = "401", description = "Quando o não for autorizado"),
		@ApiResponse(responseCode = "403", description = "Quando for proibido o acesso"),
		@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<List<CidadeDTO>> findAll() {
		List<CidadeDTO> objs = this.service.findAll();
		return ResponseEntity.ok().body(objs);
	}

	@GetMapping("/estado/{uf}")
	@Operation(summary = "Obter cidade pela UF.", tags = "Cidade")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Bem-sucedida"),
		@ApiResponse(responseCode = "401", description = "Quando o não for autorizado"),
		@ApiResponse(responseCode = "403", description = "Quando for proibido o acesso"),
		@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<List<CidadeDTO>> obterCidadeByEstado(@PathVariable Long uf) {
		List<CidadeDTO> objs = this.service.obterCidadeByEstado(uf);
		return ResponseEntity.ok().body(objs);
	}

	@GetMapping("/estados")
	@Operation(summary = "Obter todos as UFs.", tags = "Cidade")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Bem-sucedida"),
		@ApiResponse(responseCode = "401", description = "Quando o não for autorizado"),
		@ApiResponse(responseCode = "403", description = "Quando for proibido o acesso"),
		@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<List<EstadoDTO>> findUFs() {
		List<EstadoDTO> objs = this.service.findUFs();
		return ResponseEntity.ok().body(objs);
	}

}
