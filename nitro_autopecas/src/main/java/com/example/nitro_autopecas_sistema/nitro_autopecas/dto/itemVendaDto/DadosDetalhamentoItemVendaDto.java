package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.itemVendaDto;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Peca;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Venda;

import java.math.BigDecimal;

public record DadosDetalhamentoItemVendaDto(
        Long id,
        Venda venda,
        Peca peca,
        Integer quantidade,
        BigDecimal precoUnitario
) {
}
