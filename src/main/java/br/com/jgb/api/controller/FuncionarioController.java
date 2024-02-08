package br.com.jgb.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jgb.api.dto.FuncionarioDTO;
import br.com.jgb.api.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/funcionario")
@Validated
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @GetMapping("/{id}")
    @Operation(summary = "Obter dados do funcionario pelo ID.", tags = "Funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<FuncionarioDTO> findById(@PathVariable Long id) {
    	FuncionarioDTO obj = this.service.findById(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/funcionarios")
    @Operation(summary = "Obter dados de todos os funcionarios da base de dados.", tags = "Funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<List<FuncionarioDTO>> findAll() {
        List<FuncionarioDTO> objs = this.service.findAll();
        return ResponseEntity.ok().body(objs);
    }
 
    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar funcionario na base de dados.", tags = "Funcionário")
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

}
