package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.clienteDto;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Cliente;

import java.io.Serializable;

public record DadosDetalhamentoClienteDto(
        Long id,
        String nome,
        String cpf,
        String contato,
        String email,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,

        Boolean ativo
) implements Serializable {
    public DadosDetalhamentoClienteDto(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getContato(),
                cliente.getEmail(),
                cliente.getCep(),
                cliente.getLogradouro(),
                cliente.getNumero(),
                cliente.getComplemento(),
                cliente.getBairro(),
                cliente.getCidade(),
                cliente.getEstado(),

                cliente.getAtivo()
        );
    }
}
