package com.example.nitro_autopecas_sistema.nitro_autopecas.service;


import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.fornecedor.BrasilApiCnpjDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.fornecedor.DadosCadastroFornecedorDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Fornecedor;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository repository;

    @Autowired
    private com.example.nitro_autopecas_sistema.nitro_autopecas.infra.client.BrasilApiClient brasilApiClient;

    @Transactional
    public Fornecedor adicionar(DadosCadastroFornecedorDto dto) {

        // 1. Limpa a formatação (remove pontos e traços)
        String cnpjLimpo = dto.cnpj().replaceAll("[^0-9]", "");

        // 2. Valida duplicidade
        if (repository.existsByCnpj(cnpjLimpo)) {
            throw new IllegalArgumentException("Fornecedor com este CNPJ já está cadastrado.");
        }

        // 3. Busca os dados reais na Brasil API
        BrasilApiCnpjDto dadosDaApi = brasilApiClient.buscarDadosPorCnpj(cnpjLimpo);

        // 4. Converte e salva
        Fornecedor fornecedor = new Fornecedor(dadosDaApi);
        // Garante que vai salvar limpo no banco
        fornecedor.setCnpj(cnpjLimpo);

        return repository.save(fornecedor);
    }
}
