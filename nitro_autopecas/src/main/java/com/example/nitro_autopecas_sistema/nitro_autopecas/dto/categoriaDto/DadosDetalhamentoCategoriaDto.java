package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.categoriaDto;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Categoria;

public record DadosDetalhamentoCategoriaDto(
        Long id,
        String nome
) {
    public DadosDetalhamentoCategoriaDto(Categoria categoria){
        this(
                categoria.getId(),
                categoria.getNome()
        );
    }
}
