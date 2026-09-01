package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pagamentoDto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record DadosCriarPagamentoDto(
        @NotNull(message = "O ID da venda é obrigatório.")
        Long vendaId,

        @NotNull(message = "O ID do método de pagamento é obrigatório.")
        Long metodoPagamentoId,

        @NotNull(message = "O valor pago é obrigatório.")
        @Positive(message = "O valor deve ser positivo.")
        BigDecimal valorPago,

        @PositiveOrZero(message = "O valor do desconto não pode ser negativo.")
        BigDecimal desconto,

        @Min(value = 1, message = "O número de parcelas deve ser no mínimo 1.")
        Integer parcelas
) {
}
