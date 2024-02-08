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

import br.com.jgb.api.dto.ClienteDTO;
import br.com.jgb.api.dto.ClienteServicoProdutoResponse;
import br.com.jgb.api.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cliente")
@Validated
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping("/{id}")
	@Operation(summary = "Obter dados do cliente pelo ID.", tags = "Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) {
    	ClienteDTO obj = this.service.findCliente(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/clientes")
    @Operation(summary = "Obter todos clientes cadastrado na base de dados.", tags = "Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<ClienteDTO> objs = this.service.all();
        return ResponseEntity.ok().body(objs);
    }

    @PostMapping("/")
    @Validated
    @Operation(summary = "Criar um novo cliente na base de dados.", tags = "Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO obj) {
    	  var response = this.service.criar(obj);
          return ResponseEntity.ok().body(response);
    }
    
    @PutMapping("/{id}")
    @Validated
    @Operation(summary = "Atualizar dados do cliente na base de dados.", tags = "Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<ClienteDTO> update(@Valid @RequestBody ClienteDTO obj, @PathVariable Long id) {
        obj.setId(id);
        var response = this.service.update(obj);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar cliente na base de dados.", tags = "Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/clientes/idServico/{idServico}/idProduto/{idProduto}")
    @Operation(summary = "Obter dados de todos os clientes da base de dados.", tags = "Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<ClienteServicoProdutoResponse> findClientes(@PathVariable Long idServico, @PathVariable Long idProduto) {
    	ClienteServicoProdutoResponse objs = this.service.findClientesByIdServicoAndIdProduto(idServico, idProduto);
        return ResponseEntity.ok().body(objs);
    }

}
