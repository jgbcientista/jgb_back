package br.com.jgb.api.controller;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.jgb.api.dto.PessoaDTO;
import br.com.jgb.api.request.PessoaRequest;
import br.com.jgb.api.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoa")
@Validated
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/{id}")
    @Operation(summary = "Obter dados da pessoa cadastrada na base de dados pelo id.", tags = "Pessoa")
    public ResponseEntity<PessoaDTO> findById(@PathVariable Long id) {
    	PessoaDTO obj = this.pessoaService.findById(id);
        return ResponseEntity.ok(obj);
    }
    
    @GetMapping("/tipo/{tipo}/codigo/{codigo}/nome/{nome}/cpf/{cpf}")
    @Operation(summary = "Obter lista de pessoas cadastradas na base de dados com base nos filtros informados.", tags = "Pessoa")
    public ResponseEntity<List<PessoaDTO>> findFilter(@PathVariable String tipo, @PathVariable String codigo, @PathVariable String nome, @PathVariable String cpf) {
        List<PessoaDTO> objs = this.pessoaService.findFilter(tipo, codigo, nome, cpf);
        return ResponseEntity.ok().body(objs);
    }

    @GetMapping("/pessoas")
    @Operation(summary = "Obter lista de todas as pessoas cadastradas na base de dados.", tags = "Pessoa")
    public ResponseEntity<List<PessoaDTO>> findAll() {
        List<PessoaDTO> objs = this.pessoaService.findAll();
        return ResponseEntity.ok().body(objs);
    }
    
    @GetMapping("/pessoas/clientes")
    @Operation(summary = "Obtem a lista de todas as pessoas/clientes cadastradas na base de dados.", tags = "Pessoa")
    public ResponseEntity<List<PessoaDTO>> findAllClientes() {
        List<PessoaDTO> objs = this.pessoaService.findAllCliente();
        return ResponseEntity.ok().body(objs);
    }
    
    
    @GetMapping("/pessoas/fornecedores")
    @Operation(summary = "Obtem a lista de todas as pessoas/fornecedores cadastradas na base de dados.", tags = "Pessoa")
    public ResponseEntity<List<PessoaDTO>> findAllFornecedores() {
        List<PessoaDTO> objs = this.pessoaService.findAllFornecedores();
        return ResponseEntity.ok().body(objs);
    }
    
    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Obter dados da pessoa cadastrada na base de dados pelo CPF.", tags = "Pessoa")
    public ResponseEntity<PessoaDTO> findPessoaByCPF(@PathVariable String cpf) {
        PessoaDTO obj = this.pessoaService.findPessoaByCPF(cpf);
        return ResponseEntity.ok().body(obj);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados da pessoa cadastrada na base de dados.", tags = "Pessoa")
    @Validated
    public ResponseEntity<PessoaDTO> update(@PathVariable Long id, @Valid @RequestBody PessoaDTO obj) {
        obj.setId(id);
        PessoaDTO response = this.pessoaService.update(obj);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar dados da pessoa cadastrada na base de dados.", tags = "Pessoa")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    @Operation(summary = "Cadastrar nova pessoa na base de dados.", tags = "Pessoa")
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody PessoaDTO obj) {
        this.pessoaService.criar(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("associar")
    @Operation(summary = "Associar uma pessoa a um perfil na base de dados cadastrada.", tags = "Pessoa")
    @Validated
    public ResponseEntity<Void> associar(@Valid @RequestBody PessoaRequest obj) {
        this.pessoaService.associar(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
