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

import br.com.jgb.api.dto.PerfilDTO;
import br.com.jgb.api.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/perfil")
@Validated
public class PerfilController {

    @Autowired
    private PerfilService service;

    @GetMapping("/{id}")
    @Operation(summary = "Obter dados do perfil pelo ID.", tags = "Perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<PerfilDTO> findById(@PathVariable Long id) {
    	PerfilDTO obj = this.service.findById(id);
        return ResponseEntity.ok().body(obj);
    }
    
    @GetMapping("/perfis")
    @Operation(summary = "Obter todos os serviços da base de dados.", tags = "Serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<List<PerfilDTO>> findAll() {
        List<PerfilDTO> objs = this.service.findAll();
        return ResponseEntity.ok().body(objs);
    }
    
    @GetMapping("/codigo/{codigo}/descricao/{descricao}")
    @Operation(summary = "Obter lista dos perfis cadastradas na base de dados com base nos filtros informados.", tags = "Função")
    public ResponseEntity<List<PerfilDTO>> findFilter(@PathVariable String codigo, @PathVariable String descricao) {
        List<PerfilDTO> objs = this.service.findFilter(codigo, descricao);
        return ResponseEntity.ok().body(objs);
    }

    @PostMapping("/")
    @Validated
    @Operation(summary = "Criar novo perfil na base de dados.", tags = "Perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<PerfilDTO> create(@Valid @RequestBody PerfilDTO obj) {
        var response = this.service.criar(obj);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do perfil da base de dados.", tags = "Perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    @Validated
    public ResponseEntity<PerfilDTO> update(@Valid @RequestBody PerfilDTO obj, @PathVariable Long id) {
        obj.setId(id);
        var response =  this.service.update(obj);
        return ResponseEntity.ok().body(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar perfil da base de dados.", tags = "Perfil")
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
