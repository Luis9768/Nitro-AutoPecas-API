package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.List;

public record DadosCadastroPecaDto(

        @NotBlank(message = "O nome da peça é obrigatório.")
        String nome,

        @NotBlank(message = "O SKU é obrigatório.")
        String sku,

        @NotBlank(message = "O código do fabricante é obrigatório.")
        String codigoFabricante,

        String descricao,

        @NotNull(message = "O preço de custo é obrigatório.")
        @Positive(message = "O preço de custo deve ser maior que zero.")
        BigDecimal precoCusto,

        @NotNull(message = "O preço de venda é obrigatório.")
        @Positive(message = "O preço de venda deve ser maior que zero.")
        BigDecimal precoVenda,

        @NotNull(message = "A quantidade em estoque é obrigatória.")
        @PositiveOrZero(message = "A quantidade em estoque não pode ser negativa.")
        Integer quantidadeEstoque,

        @NotNull(message = "A quantidade mínima é obrigatória.")
        @PositiveOrZero(message = "A quantidade mínima não pode ser negativa.")
        Integer quantidadeMinima,

        @NotNull(message = "A quantidade máxima é obrigatória.")
        @PositiveOrZero(message = "A quantidade máxima não pode ser negativa.")
        Integer quantidadeMaxima,

        @NotNull(message = "O ID da categoria é obrigatório.")
        Long categoriaId,

        List<Long>fornecedoresIds
) {
}