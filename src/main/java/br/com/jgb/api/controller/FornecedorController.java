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

import br.com.jgb.api.dto.FornecedorDTO;
import br.com.jgb.api.dto.FornecedorServicoProdutoResponse;
import br.com.jgb.api.service.FornecedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/fornecedor")
@Validated
public class FornecedorController {

    @Autowired
    private FornecedorService service;

    @GetMapping("/{id}")
    @Operation(summary = "Obter dados do fornecedor pelo ID.", tags = "Fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<FornecedorDTO> findById(@PathVariable Long id) {
    	FornecedorDTO obj = this.service.findById(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/fornecedores")
    @Operation(summary = "Obter dados de todos os fornecedores da base de dados.", tags = "Fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<List<FornecedorDTO>> findAll() {
        List<FornecedorDTO> objs = this.service.all();
        return ResponseEntity.ok().body(objs);
    }

    @PostMapping("/")
    @Operation(summary = "Criar novo fornecedor da base de dados.", tags = "Fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<FornecedorDTO> create(@RequestBody FornecedorDTO obj) {
    	  var response = this.service.criar(obj);
          return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    @Validated
    @Operation(summary = "Atualizar dados do fornecedor da base de dados.", tags = "Fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<FornecedorDTO> update(@Valid @RequestBody FornecedorDTO obj, @PathVariable Long id) {
        obj.setId(id);
        var response = this.service.update(obj);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar fornecedor da base de dados.", tags = "Fornecedor")
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
    
    @GetMapping("/fornecedores/idServico/{idServico}/idProduto/{idProduto}")
    @Operation(summary = "Obter dados de todos os fornecedores da base de dados.", tags = "Fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<FornecedorServicoProdutoResponse> findFornecedores(@PathVariable Long idServico, @PathVariable Long idProduto) {
    	FornecedorServicoProdutoResponse objs = this.service.findFornecedoresByIdServicoAndIdProduto(idServico, idProduto);
        return ResponseEntity.ok().body(objs);
    }
    
    
    @GetMapping("/fornecedores/idServico/{idServico}")
    @Operation(summary = "Obter dados dos fornecedores cadastrados para esse servico na base de dados.", tags = "Fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<List<FornecedorDTO>> findFornecedoresByIdServico(@PathVariable Long idServico) {
    	List<FornecedorDTO> objs = this.service.findFornecedoresByIdServico(idServico);
        return ResponseEntity.ok().body(objs);
    }
    
    
    @GetMapping("/fornecedores/idProduto/{idProduto}")
    @Operation(summary = "Obter dados dos fornecedores cadastrados para esse servico na base de dados.", tags = "Fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<List<FornecedorDTO>> findFornecedoresByIdProduto(@PathVariable Long idProduto) {
    	List<FornecedorDTO> objs = this.service.findFornecedoresByIdProduto(idProduto);
        return ResponseEntity.ok().body(objs);
    }
    
    
    @GetMapping("/fornecedores/nao-vinculado/idServico/{idServico}")
    @Operation(summary = "Obter dados dos fornecedores cadastrados para esse servico na base de dados.", tags = "Fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<List<FornecedorDTO>> findFornecedoresNaoVinculados(@PathVariable Long idServico) {
    	List<FornecedorDTO> objs = this.service.findFornecedoresNaoVinculados(idServico);
        return ResponseEntity.ok().body(objs);
    }
    
    @DeleteMapping("/fornecedor-servico/{id}")
    @Operation(summary = "Desativar associação de serviço fornecedor da base de dados.", tags = "Fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Quando não for encontrado"),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro ao processar"),
            @ApiResponse(responseCode = "500", description = "Quando ocorrer erro do servidor") 
    })
    public ResponseEntity<Void> fornecedorServicoRepository(@PathVariable Long id) {
        this.service.deleteFornecedorServico(id);
        return ResponseEntity.noContent().build();
    }
   
}
