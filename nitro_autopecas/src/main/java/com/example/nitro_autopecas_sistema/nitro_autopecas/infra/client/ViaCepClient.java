package com.example.nitro_autopecas_sistema.nitro_autopecas.infra.client;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.viaCepDto.ViaCepDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/ws")
public interface ViaCepClient {

    @GetExchange("/{cep}/json/")
    ViaCepDto buscarEnderecoPorCep(@PathVariable("cep") String cep);
}
