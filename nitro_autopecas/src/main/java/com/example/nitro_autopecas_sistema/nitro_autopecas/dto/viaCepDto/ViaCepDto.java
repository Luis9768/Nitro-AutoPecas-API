package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.viaCepDto;

public record ViaCepDto(
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String uf
) {}
