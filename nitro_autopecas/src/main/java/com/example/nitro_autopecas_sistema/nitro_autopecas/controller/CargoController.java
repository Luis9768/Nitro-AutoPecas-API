package com.example.nitro_autopecas_sistema.nitro_autopecas.controller;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cargoDto.DadosAtualizarCargoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cargoDto.DadosCadastroCargoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cargoDto.DadosDetalhamentoCargoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cliente.DadosCadastroClienteDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cliente.DadosDetalhamentoClienteDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.service.CargoService;
import com.example.nitro_autopecas_sistema.nitro_autopecas.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping("/cargo")
public class CargoController {

    @Autowired
    private CargoService service;

    @PostMapping
    @PreAuthorize("hasRole('FUNCIONARIO')")
    public ResponseEntity<DadosDetalhamentoCargoDto> adicionar(@Valid @RequestBody DadosCadastroCargoDto dto){
        DadosDetalhamentoCargoDto cargo = service.adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cargo);
    }

    @GetMapping
    @PreAuthorize("hasRole('FUNCIONARIO')")
    public ResponseEntity<List<DadosDetalhamentoCargoDto>> listar(){
        return ResponseEntity.ok(service.listar());
    }
    @PutMapping
    @PreAuthorize("hasRole('FUNCIONARIO')")
    public ResponseEntity<DadosDetalhamentoCargoDto> atualizar(@PathVariable Long id, @Valid @RequestBody DadosAtualizarCargoDto dto){
        DadosDetalhamentoCargoDto cargoDto = service.atualizar(id, dto);
        if (cargoDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cargoDto);
    }
    @DeleteMapping
    @PreAuthorize("hasRole('FUNCIONARIO')")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.desativarCargo(id);
        return ResponseEntity.noContent().build();
    }
}
