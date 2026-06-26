package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.fornecedor;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroFornecedorDto(
        @NotBlank(message = "O CNPJ é obrigatório")
        String cnpj
) {}
