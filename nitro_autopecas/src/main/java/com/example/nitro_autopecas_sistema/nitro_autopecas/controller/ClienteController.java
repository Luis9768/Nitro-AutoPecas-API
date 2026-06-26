package com.example.nitro_autopecas_sistema.nitro_autopecas.controller;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cliente.DadosAtualizarClienteDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cliente.DadosCadastroClienteDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cliente.DadosDetalhamentoClienteDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Usuario;
import com.example.nitro_autopecas_sistema.nitro_autopecas.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoClienteDto> cadastrarUsuario(@Valid @RequestBody DadosCadastroClienteDto dto) {
        DadosDetalhamentoClienteDto usuario = service.adicionarCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoClienteDto> atualizarDados(@PathVariable Long id, @Valid @RequestBody DadosAtualizarClienteDto dto, @AuthenticationPrincipal Usuario usuarioLogado) {
        DadosDetalhamentoClienteDto usuarioAtualizado = service.atualizar(id, dto, usuarioLogado);
        if (usuarioAtualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado) {
        service.excluirUsuarioId(id, usuarioLogado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoClienteDto>> listarUsuarios (@AuthenticationPrincipal Usuario usuarioLogado) {
        return ResponseEntity.ok(service.listarUsuarios(usuarioLogado));
    }

    //@PreAuthorize("hasRole('FUNCIONARIO')")
    @GetMapping("/buscarPorNome/{nome}")
    public ResponseEntity<List<DadosDetalhamentoClienteDto>> buscarClienteNome (@PathVariable String nome, @AuthenticationPrincipal Usuario usuarioLogado){
        var buscarNome = service.pesquisarPorNome(nome,  usuarioLogado);
        return ResponseEntity.ok(buscarNome);
    }
}
