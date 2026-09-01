package com.example.nitro_autopecas_sistema.nitro_autopecas.controller;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pagamentoDto.DadosCriarPagamentoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pagamentoDto.DadosDetalhamentoPagamentoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.MetodoPagamento;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.StatusPagamento;
import com.example.nitro_autopecas_sistema.nitro_autopecas.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    PagamentoService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoPagamentoDto> criarPagamento(@RequestBody DadosCriarPagamentoDto dto){
        var a = service.criarPagamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(a);
    }
    @GetMapping("/venda/{vendaId}")
    public ResponseEntity<List<DadosDetalhamentoPagamentoDto>> buscarPorVendaId(@PathVariable Long vendaId) {
        List<DadosDetalhamentoPagamentoDto> lista = service.buscarPagamentosPorVendaId(vendaId);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<DadosDetalhamentoPagamentoDto>> buscarPorStatus(@PathVariable StatusPagamento status) {
        List<DadosDetalhamentoPagamentoDto> lista = service.buscarPagamentosPorStatus(status);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/metodo/{metodo}")
    public ResponseEntity<List<DadosDetalhamentoPagamentoDto>> buscarPorMetodo(@PathVariable MetodoPagamento metodo) {
        List<DadosDetalhamentoPagamentoDto> lista = service.buscarPagamentosPorMetodoPagamento(metodo);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<DadosDetalhamentoPagamentoDto>> buscarPorStatusEMetodo(
            @RequestParam StatusPagamento status,
            @RequestParam MetodoPagamento metodo) {
        List<DadosDetalhamentoPagamentoDto> lista = service.buscarPorStatusEMetodoDePagamento(status, metodo);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/data")
    public ResponseEntity<List<DadosDetalhamentoPagamentoDto>> buscarPorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime data) {
        List<DadosDetalhamentoPagamentoDto> lista = service.buscarPagamentosPorDataPagamento(data);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<DadosDetalhamentoPagamentoDto>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        List<DadosDetalhamentoPagamentoDto> lista = service.buscarPagamentosPorDataPagamentoEntre(dataInicio, dataFim);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/status-periodo")
    public ResponseEntity<List<DadosDetalhamentoPagamentoDto>> buscarPorStatusEPeriodo(
            @RequestParam StatusPagamento status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        List<DadosDetalhamentoPagamentoDto> lista = service.buscarPagamentoPorStatusEDataPagamentoEntre(status, dataInicio, dataFim);
        return ResponseEntity.ok(lista);
    }
}
