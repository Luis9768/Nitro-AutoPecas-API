package com.example.nitro_autopecas_sistema.nitro_autopecas.controller;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaDto.DadosDetalhamentoPecaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaFornecedorDto.DadosDetalhamentoPecaFornecedorDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.service.PecaFornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/peca-fornecedor")
public class PecaFornecedorController {
    @Autowired
    PecaFornecedorService service;

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoPecaFornecedorDto>> listar(){
        List<DadosDetalhamentoPecaFornecedorDto> pecas = service.listar();
        return ResponseEntity.ok(pecas);
    }
}
