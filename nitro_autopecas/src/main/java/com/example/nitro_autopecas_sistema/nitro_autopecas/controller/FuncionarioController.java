package com.example.nitro_autopecas_sistema.nitro_autopecas.controller;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cliente.DadosAtualizarClienteDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cliente.DadosDetalhamentoClienteDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.funcionario.DadosAtualizarFuncionarioDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.funcionario.DadosCadastroFuncionarioDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.funcionario.DadosDetalhamentoFuncionarioDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Usuario;
import com.example.nitro_autopecas_sistema.nitro_autopecas.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    FuncionarioService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoFuncionarioDto> adicionar(@Valid @RequestBody DadosCadastroFuncionarioDto dto){
        DadosDetalhamentoFuncionarioDto funcionarioDto  = service.adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioDto);
    }
    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoFuncionarioDto>> listar(@AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(service.listar(usuario));
    }
    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoFuncionarioDto> atualizarDados(@PathVariable Long id, @Valid @RequestBody DadosAtualizarFuncionarioDto dto, @AuthenticationPrincipal Usuario usuarioLogado) {
        DadosDetalhamentoFuncionarioDto usuarioAtualizado = service.atualizar(id, dto, usuarioLogado);
        if (usuarioAtualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioAtualizado);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoFuncionarioDto> buscarPorId(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado){
        DadosDetalhamentoFuncionarioDto listarId = service.buscarPorId(id, usuarioLogado);
        return ResponseEntity.ok(listarId);
    }
    //@PreAuthorize("hasRole('FUNCIONARIO')")
    @GetMapping("/buscarPorNome/{nome}")
    public ResponseEntity<List<DadosDetalhamentoFuncionarioDto>> buscarClienteNome (@PathVariable String nome, @AuthenticationPrincipal Usuario usuarioLogado){
        List<DadosDetalhamentoFuncionarioDto> buscarNome = service.pesquisarPorNome(nome,  usuarioLogado);
        return ResponseEntity.ok(buscarNome);
    }


}
