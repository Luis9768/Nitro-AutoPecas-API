package com.example.nitro_autopecas_sistema.nitro_autopecas.controller;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.vendaDto.DadosCriarVendaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.vendaDto.DadosDetalhamentoVendaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    VendaService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoVendaDto> adicionar(@RequestBody DadosCriarVendaDto dto){
        DadosDetalhamentoVendaDto venda = service.adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(venda);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarCompra(@RequestParam Long id){
        service.cancelarVenda(id);
        return ResponseEntity.noContent().build();
    }
}
