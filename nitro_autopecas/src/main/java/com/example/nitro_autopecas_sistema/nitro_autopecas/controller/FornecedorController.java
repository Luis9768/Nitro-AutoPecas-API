package com.example.nitro_autopecas_sistema.nitro_autopecas.controller;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.fornecedorDto.DadosCadastroFornecedorDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.fornecedorDto.DadosDetalhamentoFornecedorDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Fornecedor;
import com.example.nitro_autopecas_sistema.nitro_autopecas.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService service;

    @PostMapping
    @PreAuthorize("hasRole('FUNCIONARIO')")
    public ResponseEntity<Fornecedor> adicionar(@Valid @RequestBody DadosCadastroFornecedorDto dto) {
        Fornecedor fornecedorSalvo = service.adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalvo);
    }
    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoFornecedorDto>> listar(){
        return ResponseEntity.ok(service.listar());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }

}
