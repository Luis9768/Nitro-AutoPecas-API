package com.example.nitro_autopecas_sistema.nitro_autopecas.infra.client;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.fornecedor.BrasilApiCnpjDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/cnpj/v1")
public interface BrasilApiClient {

    @GetExchange("/{cnpj}")
    BrasilApiCnpjDto buscarDadosPorCnpj(@PathVariable("cnpj") String cnpj);
}
