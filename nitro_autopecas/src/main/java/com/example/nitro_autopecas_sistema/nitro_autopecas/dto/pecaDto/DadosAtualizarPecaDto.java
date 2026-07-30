package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record DadosAtualizarPecaDto(
        String nome,
        String sku,
        String codigoFabricante,
        String descricao,
        BigDecimal precoCusto,
        BigDecimal precoVenda,
        Integer quantidadeEstoque,
        Integer quantidadeMinima,
        Integer quantidadeMaxima,
        Long categoriaId
) {
}
