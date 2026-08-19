package com.example.nitro_autopecas_sistema.nitro_autopecas.controller;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.categoriaDto.DadosCadastroCategoriaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.categoriaDto.DadosDetalhamentoCategoriaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.metodoPagamentoDto.DadosCadastroMetodoPagamentoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.metodoPagamentoDto.DadosDetalhamentoMetodoPagamentoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.service.MetodoPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metodoPagamento")
public class MetodoPagamentoController {
    @Autowired
    MetodoPagamentoService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoMetodoPagamentoDto> adicionar(@RequestBody DadosCadastroMetodoPagamentoDto dto){
        DadosDetalhamentoMetodoPagamentoDto metodoPagamento = service.adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(metodoPagamento);
    }
    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMetodoPagamentoDto> atualizar(@PathVariable Integer id, @RequestBody DadosCadastroMetodoPagamentoDto dto){
        DadosDetalhamentoMetodoPagamentoDto categoria = service.atualizar(id,dto);
        return ResponseEntity.ok(categoria);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Integer id){
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoMetodoPagamentoDto>> listar(){
        return ResponseEntity.ok(service.listar());
    }
}
