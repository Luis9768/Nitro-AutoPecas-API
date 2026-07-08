package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.fornecedor;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Fornecedor;
import jakarta.persistence.Column;

public record DadosDetalhamentoFornecedorDto(
        Long id,
        String cnpj,
        String razaoSocial,
        String nomeFantasia,
        String telefone,
        String email,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        Boolean ativo
) {
    public DadosDetalhamentoFornecedorDto(Fornecedor fornecedor){
        this(
                fornecedor.getId(),
                fornecedor.getCnpj(),
                fornecedor.getRazaoSocial(),
                fornecedor.getNomeFantasia(),
                fornecedor.getTelefone(),
                fornecedor.getEmail(),
                fornecedor.getCep(),
                fornecedor.getLogradouro(),
                fornecedor.getNumero(),
                fornecedor.getComplemento(),
                fornecedor.getBairro(),
                fornecedor.getCidade(),
                fornecedor.getEstado(),
                fornecedor.getAtivo()
        );
    }
}
