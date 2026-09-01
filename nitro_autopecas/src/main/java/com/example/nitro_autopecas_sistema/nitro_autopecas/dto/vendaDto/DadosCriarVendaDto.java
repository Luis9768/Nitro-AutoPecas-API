package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.vendaDto;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.itemVendaDto.DadosItemVendaDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record DadosCriarVendaDto(

        @NotNull(message = "O ID do cliente é obrigatório.")
        UUID clienteId,

        @NotNull(message = "O ID do funcionário é obrigatório.")
        UUID funcionarioId,

        @NotEmpty(message = "A venda deve conter pelo menos um item.")
        @Valid
        List<DadosItemVendaDto> itens
) {
}