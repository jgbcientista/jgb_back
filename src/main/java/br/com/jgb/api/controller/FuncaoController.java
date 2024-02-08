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

import br.com.jgb.api.dto.FuncaoDTO;
import br.com.jgb.api.service.FuncaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/funcao")
@Validated
public class FuncaoController {

    @Autowired
    private FuncaoService service;

    @GetMapping("/{id}")
    @Operation(summary = "Obter dados da função pelo ID.", tags = "Função")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<FuncaoDTO> findById(@PathVariable Long id) {
    	FuncaoDTO obj = this.service.findById(id);
        return ResponseEntity.ok(obj);
    }
    
    @GetMapping("/codigo/{codigo}/descricao/{descricao}")
    @Operation(summary = "Obter lista de funções profissionais cadastradas na base de dados com base nos filtros informados.", tags = "Função")
    public ResponseEntity<List<FuncaoDTO>> findFilter(@PathVariable String codigo, @PathVariable String descricao) {
        List<FuncaoDTO> objs = this.service.findFilter(codigo, descricao);
        return ResponseEntity.ok().body(objs);
    }

    @GetMapping("/funcoes")
    @Operation(summary = "Obter todos os dados da função pelo ID.", tags = "Função")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<List<FuncaoDTO>> findAll() {
        List<FuncaoDTO> objs = this.service.findAll();
        return ResponseEntity.ok().body(objs);
    }

    @PostMapping("/")
    @Validated
    @Operation(summary = "Criar nova função na base de dados.", tags = "Função")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<FuncaoDTO> criar(@Valid @RequestBody FuncaoDTO obj) {
        var response = this.service.criar(obj);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados da função na base de dados.", tags = "Função")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    @Validated
    public ResponseEntity<FuncaoDTO> update(@Valid @RequestBody FuncaoDTO obj, @PathVariable Long id) {
        obj.setId(id);
        var response =  this.service.update(obj);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar função pelo ID.", tags = "Função")
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
