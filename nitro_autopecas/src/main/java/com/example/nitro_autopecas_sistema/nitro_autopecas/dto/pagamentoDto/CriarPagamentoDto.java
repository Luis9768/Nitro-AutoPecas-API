package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pagamentoDto;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.MetodoPagamento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CriarPagamentoDto(
        @NotNull(message = "O ID da venda é obrigatório.")
        Long vendaId,

        @NotNull(message = "O método de pagamento é obrigatório.")
        MetodoPagamento metodoPagamento,

        @NotNull(message = "O valor do pagamento é obrigatório.")
        @Positive(message = "O valor do pagamento deve ser maior que zero.")
        BigDecimal valorPago,

        // Usamos PositiveOrZero para aceitar 0 ou valores positivos, rejeitando números negativos
        @PositiveOrZero(message = "O valor do desconto não pode ser negativo.")
        BigDecimal desconto,

        @Min(value = 1, message = "O número de parcelas deve ser no mínimo 1.")
        Integer parcelas
) {
}
