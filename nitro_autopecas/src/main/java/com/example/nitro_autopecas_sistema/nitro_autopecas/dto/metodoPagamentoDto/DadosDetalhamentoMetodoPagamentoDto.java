package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.metodoPagamentoDto;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.MetodoPagamento;

import java.io.Serializable;

public record DadosDetalhamentoMetodoPagamentoDto(
        Integer id,
        String nome
) implements Serializable {
    public DadosDetalhamentoMetodoPagamentoDto(MetodoPagamento metodoPagamento){
        this(
                metodoPagamento.getId(),
                metodoPagamento.getNome()
        );
    }
}
