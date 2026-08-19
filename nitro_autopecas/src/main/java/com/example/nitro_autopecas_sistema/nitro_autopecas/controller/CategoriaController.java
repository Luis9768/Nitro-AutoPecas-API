package com.example.nitro_autopecas_sistema.nitro_autopecas.controller;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cargoDto.DadosDetalhamentoCargoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.categoriaDto.DadosCadastroCategoriaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.categoriaDto.DadosDetalhamentoCategoriaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Categoria;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.CategoriaRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    CategoriaService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoCategoriaDto> adicionar(@RequestBody DadosCadastroCategoriaDto dto){
        DadosDetalhamentoCategoriaDto categoria = service.adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }
    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCategoriaDto> atualizar(@PathVariable Long id, @RequestBody DadosCadastroCategoriaDto dto){
        DadosDetalhamentoCategoriaDto categoria = service.atualizar(id,dto);
        return ResponseEntity.ok(categoria);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id){
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoCategoriaDto>> listar(){
        return ResponseEntity.ok(service.listar());
    }
}
