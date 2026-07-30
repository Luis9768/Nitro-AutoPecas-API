package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaDto;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Peca;

import java.io.Serializable;
import java.math.BigDecimal;

public record DadosDetalhamentoPecaDto(
        Long id,
        String nome,
        String sku,
        String codigoFabricante,
        String descricao,
        BigDecimal precoCusto,
        BigDecimal precoVenda,
        Integer quantidadeEstoque,
        Integer quantidadeMinima,
        Integer quantidadeMaxima,
        Long categoriaId,
        String categoriaNome,
        Boolean ativo
)implements Serializable {
    public DadosDetalhamentoPecaDto(Peca peca) {
        this(
                peca.getId(),
                peca.getNome(),
                peca.getSku(),
                peca.getCodigoFabricante(),
                peca.getDescricao(),
                peca.getPrecoCusto(),
                peca.getPrecoVenda(),
                peca.getQuantidadeEstoque(),
                peca.getQuantidadeMinima(),
                peca.getQuantidadeMaxima(),
                // Pega os dados da categoria apenas se ela não for nula
                peca.getCategoria() != null ? peca.getCategoria().getId() : null,
                peca.getCategoria() != null ? peca.getCategoria().getNome() : null,
                peca.getAtivo()
        );
    }
}
