package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.vendaDto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DadosItemVendaDto(
        @NotNull(message = "O ID da peça é obrigatório.")
        Long pecaId,

        @NotNull(message = "A quantidade é obrigatória.")
        @Min(value = 1, message = "A quantidade mínima para venda é 1.")
        Integer quantidade
) {
}