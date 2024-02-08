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

import br.com.jgb.api.dto.ProdutoDTO;
import br.com.jgb.api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto")
@Validated
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping("/{id}")
    @Operation(summary = "Obter dados do produto pelo ID.", tags = "Produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id) {
    	ProdutoDTO obj = this.service.findById(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/produtos")
    @Operation(summary = "Obter todos os produtos da base de dados.", tags = "Produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<List<ProdutoDTO>> findAll() {
        List<ProdutoDTO> objs = this.service.findAll();
        return ResponseEntity.ok().body(objs);
    }

    
    @GetMapping("/codigo/{codigo}/descricao/{descricao}")
    @Operation(summary = "Obter lista de produtos cadastrados na base de dados com base nos filtros informados.", tags = "Produto")
    public ResponseEntity<List<ProdutoDTO>> findFilter(@PathVariable String codigo, @PathVariable String descricao) {
        List<ProdutoDTO> objs = this.service.findFilter(codigo, descricao);
        return ResponseEntity.ok().body(objs);
    }
    
    @PostMapping("/")
    @Validated
    @Operation(summary = "Criar dados do produto da base de dados.", tags = "Produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<ProdutoDTO> create(@Valid @RequestBody ProdutoDTO obj) {
    	var response = this.service.criar(obj);
		return ResponseEntity.ok().body(response);
    }
    
    @PutMapping("/{id}")
    @Validated
    @Operation(summary = "Atualizar dados do produto na base de dados.", tags = "Produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<ProdutoDTO> update(@Valid @RequestBody ProdutoDTO obj, @PathVariable Long id) {
        obj.setId(id);
        var response = this.service.update(obj);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar produto da base de dados.", tags = "Produto")
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
    
	@DeleteMapping("/idProduto/{idProduto}/idFornecedor/{idFornecedor}")
	@Operation(summary = "Desativar serviço da base de dados.", tags = "Produto")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
			@ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
			@ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
			@ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") })
	public ResponseEntity<ProdutoDTO> delete(@PathVariable Long idProduto, @PathVariable Long idFornecedor) {
		ProdutoDTO response = this.service.removerAssociacaoComProduto(idProduto, idFornecedor);
		 return ResponseEntity.ok().body(response);
	}

}
