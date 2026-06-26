package com.example.nitro_autopecas_sistema.nitro_autopecas.controller;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.fornecedor.DadosCadastroFornecedorDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Fornecedor;
import com.example.nitro_autopecas_sistema.nitro_autopecas.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService service;

    @PostMapping
    public ResponseEntity<Fornecedor> adicionar(@Valid @RequestBody DadosCadastroFornecedorDto dto) {
        Fornecedor fornecedorSalvo = service.adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalvo);
    }
}
