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

import br.com.jgb.api.dto.PatrimonioDTO;
import br.com.jgb.api.entity.enums.TipoPatrimonioEnum;
import br.com.jgb.api.service.PatrimonioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/patrimonio")
@Validated
public class PatrimonioController {

    @Autowired
    private PatrimonioService service;

    @GetMapping("/{id}")
    @Operation(summary = "Obter dados do patrimonio pelo ID.", tags = "Patrimonio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<PatrimonioDTO> findById(@PathVariable Long id) {
    	PatrimonioDTO obj = this.service.findById(id);
        return ResponseEntity.ok(obj);
    }
    
    @GetMapping("/codigo/{codigo}/descricao/{descricao}/tipoPatrimonio/{tipoPatrimonio}")
    @Operation(summary = "Obter lista de serviços cadastrados na base de dados com base nos filtros informados.", tags = "Patrimonio")
    public ResponseEntity<List<PatrimonioDTO>> findFilter(@PathVariable String codigo, @PathVariable String descricao, @PathVariable String tipoPatrimonio) {
        List<PatrimonioDTO> objs = this.service.findFilter(codigo, descricao, tipoPatrimonio);
        return ResponseEntity.ok().body(objs);
    }

    @GetMapping("/patrimonios")
    @Operation(summary = "Obter todos os patrimônios da base de dados.", tags = "Patrimonio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<List<PatrimonioDTO>> findAll() {
        List<PatrimonioDTO> objs = this.service.findAll();
        return ResponseEntity.ok().body(objs);
    }
    

    @GetMapping("/tiposPatrimonios")
    @Operation(summary = "Obtem a lista dos tipos de patrimônios", tags = "Patrimonio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<List<String>> findTiposPatrimonios() {
        return ResponseEntity.ok().body(TipoPatrimonioEnum.findTiposPatrimonios());
    }

    @PostMapping("/")
    @Validated
    @Operation(summary = "Criar dados do patrimônio na base de dados.", tags = "Patrimonio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<PatrimonioDTO> create(@Valid @RequestBody PatrimonioDTO obj) {
    	   var response = this.service.criar(obj);
           return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    @Validated
    @Operation(summary = "Atualizar dados do patrimônio da base de dados.", tags = "Patrimonio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<PatrimonioDTO> update(@Valid @RequestBody PatrimonioDTO obj, @PathVariable Long id) {
        obj.setId(id);
        var response =  this.service.update(obj);
        return ResponseEntity.ok().body(response);
        
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar patrimônio da base de dados.", tags = "Patrimonio")
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
