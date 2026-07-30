package com.example.nitro_autopecas_sistema.nitro_autopecas.controller;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaDto.DadosAtualizarPecaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaDto.DadosCadastroPecaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaDto.DadosDetalhamentoPecaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.service.PecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/peca")
public class PecaController {

    @Autowired
    private PecaService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoPecaDto> adicionar(@RequestBody DadosCadastroPecaDto dto){
        DadosDetalhamentoPecaDto peca = service.adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(peca);
    }
    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoPecaDto>> listar() {
        List<DadosDetalhamentoPecaDto> pecas = service.listar();
        return ResponseEntity.ok(pecas);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPecaDto> buscarPorId(@PathVariable Long id) {
        DadosDetalhamentoPecaDto peca = service.buscarPorId(id);
        return ResponseEntity.ok(peca);
    }
    @GetMapping("/sku/{sku}")
    public ResponseEntity<DadosDetalhamentoPecaDto> buscarPorSku(@PathVariable String sku) {
        DadosDetalhamentoPecaDto peca = service.buscarPorSku(sku);
        return ResponseEntity.ok(peca);
    }

    @GetMapping("/fabricante/{codigoFabricante}")
    public ResponseEntity<DadosDetalhamentoPecaDto> buscarPorCodigoFabricante(@PathVariable String codigoFabricante) {
        DadosDetalhamentoPecaDto peca = service.buscarPorCodigoFabricante(codigoFabricante);
        return ResponseEntity.ok(peca);
    }
    @GetMapping("/buscar")
    public ResponseEntity<List<DadosDetalhamentoPecaDto>> buscarPorNome(@RequestParam String nome) {
        List<DadosDetalhamentoPecaDto> pecas = service.buscarPorNome(nome);
        return ResponseEntity.ok(pecas);
    }
    @GetMapping("/estoqueAbaixoDoMinimo")
    public ResponseEntity<List<DadosDetalhamentoPecaDto>> buscarPecasComEstoqueAbaixoDoMinimo() {
        List<DadosDetalhamentoPecaDto> buscarPecasComEstoqueAbaixoDoMinimo = service.buscarPecasComEstoqueAbaixoDoMinimo();
        return ResponseEntity.ok(buscarPecasComEstoqueAbaixoDoMinimo);
    }

    @GetMapping("/estoqueAcimaDoMaximo")
    public ResponseEntity<List<DadosDetalhamentoPecaDto>> buscarPecasComEstoqueAcimaDoMaximo() {
        List<DadosDetalhamentoPecaDto> buscarPecasComEstoqueAcimaDoMaximo = service.buscarPecasComEstoqueAbaixoDoMinimo();
        return ResponseEntity.ok(buscarPecasComEstoqueAcimaDoMaximo);
    }
    @DeleteMapping("/intivar")
    public ResponseEntity<Void> inativar(@PathVariable Long id){
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/atualizar")
    public ResponseEntity<DadosDetalhamentoPecaDto> atualizar(@PathVariable Long id, @RequestBody DadosAtualizarPecaDto dto){
        DadosDetalhamentoPecaDto pecaAtualizada = service.atualizar(id, dto);
        return ResponseEntity.ok(pecaAtualizada);
    }
    @GetMapping("/busca")
    public ResponseEntity<List<DadosDetalhamentoPecaDto>> buscaGlobal(@RequestParam String termo) {
        var pecas = service.buscaGlobal(termo);
        return ResponseEntity.ok(pecas);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<DadosDetalhamentoPecaDto>> buscarPorCategoria(@PathVariable Long categoriaId) {
        var pecas = service.buscarPorCategoria(categoriaId);
        return ResponseEntity.ok(pecas);
    }

    @GetMapping("/estoque-superfaturado")
    public ResponseEntity<List<DadosDetalhamentoPecaDto>> buscarPecasComEstoqueSuperfaturado() {
        var pecas = service.buscarPecasComEstoqueSuperfaturado();
        return ResponseEntity.ok(pecas);
    }

    @GetMapping("/valor-total-custo")
    public ResponseEntity<BigDecimal> calcularValorTotalEstoqueCusto() {
        var valorTotal = service.calcularValorTotalEstoqueCusto();
        return ResponseEntity.ok(valorTotal);
    }

    @GetMapping("/valor-total-venda")
    public ResponseEntity<BigDecimal> calcularValorTotalEstoqueVenda() {
        var valorTotal = service.calcularValorTotalEstoqueVenda();
        return ResponseEntity.ok(valorTotal);
    }

    @GetMapping("/esgotadas")
    public ResponseEntity<List<DadosDetalhamentoPecaDto>> buscarPecasEsgotadas() {
        var pecas = service.buscarPecasEsgotadas();
        return ResponseEntity.ok(pecas);
    }
}
