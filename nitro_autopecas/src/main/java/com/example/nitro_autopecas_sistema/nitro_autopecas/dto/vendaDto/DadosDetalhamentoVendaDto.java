package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.vendaDto;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.StatusVenda;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DadosDetalhamentoVendaDto(
        Long id,
        LocalDateTime dataVenda,
        BigDecimal valorTotal,
        UUID clienteId,
        UUID funcionarioId,
        StatusVenda status
) {
    // Construtor que mapeia a entidade Venda para o DTO automaticamente
    public DadosDetalhamentoVendaDto(Venda venda) {
        this(
                venda.getId(),
                venda.getDataVenda(),
                venda.getValorTotal(),
                venda.getCliente().getId(),
                venda.getFuncionario().getId(),
                venda.getStatus()
        );
    }
}