package br.com.jgb.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jgb.api.dto.ServicoVinculadoAndNaoVinculadoDTO;
import br.com.jgb.api.dto.ServicoDTO;
import br.com.jgb.api.service.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/servico")
@Validated
public class ServicoController {

	@Autowired
	private ServicoService service;

	@GetMapping("/{id}")
	@Operation(summary = "Obter dados do serviço pelo ID.", tags = "Serviço")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
			@ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
			@ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
			@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<ServicoDTO> findById(@PathVariable Long id) {
		ServicoDTO obj = this.service.findById(id);
		return ResponseEntity.ok(obj);
	}

	@GetMapping("/codigo/{codigo}/descricao/{descricao}")
	@Operation(summary = "Obter lista de serviços cadastrados na base de dados com base nos filtros informados.", tags = "Serviço")
	public ResponseEntity<List<ServicoDTO>> findFilter(@PathVariable String codigo, @PathVariable String descricao) {
		List<ServicoDTO> objs = this.service.findFilter(codigo, descricao);
		return ResponseEntity.ok().body(objs);
	}

	@GetMapping("/servicos")
	@Operation(summary = "Obter todos os serviços da base de dados.", tags = "Serviço")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
			@ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
			@ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
			@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<List<ServicoDTO>> findAll() {
		List<ServicoDTO> objs = this.service.findAll();
		return ResponseEntity.ok().body(objs);
	}

	@PostMapping("/")
	@Validated
	@Operation(summary = "Criar dados do serviço da base de dados.", tags = "Serviço")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
			@ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
			@ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
			@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<ServicoDTO> create(@Valid @RequestBody ServicoDTO obj) {
		var response = this.service.criar(obj);
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/{id}")
	@Validated
	@Operation(summary = "Atualizar dados do serviço da base de dados.", tags = "Serviço")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
			@ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
			@ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
			@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<ServicoDTO> update(@Valid @RequestBody ServicoDTO obj, @PathVariable Long id) {
		obj.setId(id);
		var response = this.service.update(obj);
		return ResponseEntity.ok().body(response);
	}
	

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar dados do serviço cadastrada na base de dados.", tags = "Pessoa")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

	@DeleteMapping("/idServico/{idServico}/idCliente/{idCliente}")
	@Operation(summary = "Desativar serviço da base de dados.", tags = "Serviço")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
			@ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
			@ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
			@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<ServicoDTO> delete(@PathVariable Long idServico, @PathVariable Long idCliente) {
		var response = this.service.removerAssociacaoComServicoCliente(idServico, idCliente);
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping("/idServico/{idServico}/idFornecedor/{idFornecedor}")
	@Operation(summary = "Desativar serviço da base de dados.", tags = "Serviço")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
			@ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
			@ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
			@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<ServicoDTO> removerAssociacaoServicoFornecedor(@PathVariable Long idServico, @PathVariable Long idFornecedor) {
		var response = this.service.removerAssociacaoComServicoFornecedor(idServico, idFornecedor);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/contratado-nao-contrados/idPessoa/{idPessoa}")
	@Operation(summary = "Obter os serviços e produtos contratados e nao contratados", tags = "Serviço")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
			@ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
			@ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
			@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<ServicoVinculadoAndNaoVinculadoDTO> obterServicosContratdosNaoContradosCliente(
			@PathVariable Long idPessoa) {
		ServicoVinculadoAndNaoVinculadoDTO objs = this.service.obterServicosContratdosNaoContrados(idPessoa);
		return ResponseEntity.ok().body(objs);
	}
	
}
