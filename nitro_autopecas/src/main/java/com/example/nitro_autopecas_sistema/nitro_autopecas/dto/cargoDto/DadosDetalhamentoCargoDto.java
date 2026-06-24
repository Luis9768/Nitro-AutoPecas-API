package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cargoDto;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Cargo;

public record DadosDetalhamentoCargoDto(
        Long id,
        String nome,
        String descricao,
        Boolean ativo
) {
    public DadosDetalhamentoCargoDto(Cargo cargo){
        this(
                cargo.getId(),
                cargo.getNome(),
                cargo.getDescricao(),
                cargo.getAtivo()
        );
    }
}
