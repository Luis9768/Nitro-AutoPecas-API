package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cliente;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Cliente;

public record DadosDetalhamentoClienteDto(
        Long id,
        String nome,
        String cpf,
        String contato,
        String email
) {
    public DadosDetalhamentoClienteDto(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getContato(),
                cliente.getEmail()
        );
    }
}
