package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.fornecedor;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BrasilApiCnpjDto(
        String cnpj,
        @JsonProperty("razao_social") String razaoSocial,
        @JsonProperty("nome_fantasia") String nomeFantasia,
        @JsonProperty("ddd_telefone_1") String telefone,
        String email,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String municipio,
        String uf
) {}
