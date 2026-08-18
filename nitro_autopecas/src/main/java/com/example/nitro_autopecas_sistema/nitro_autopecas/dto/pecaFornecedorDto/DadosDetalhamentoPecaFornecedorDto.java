package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaFornecedorDto;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.PecaFornecedor;

import java.io.Serializable;

public record DadosDetalhamentoPecaFornecedorDto(
        Long pecaFornecedorId,
        Long fornecedorId,
        Long pecaId,
        String codigoReferenciaFornecedor
)implements Serializable {
    public DadosDetalhamentoPecaFornecedorDto(PecaFornecedor pecaFornecedor){
        this(
                pecaFornecedor.getId(),
                pecaFornecedor.getFornecedor().getId(),
                pecaFornecedor.getPeca().getId(),
                pecaFornecedor.getCodigoReferenciaFornecedor()
        );
    }
}
