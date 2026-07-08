package com.example.nitro_autopecas_sistema.nitro_autopecas.service;


import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.fornecedor.BrasilApiCnpjDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.fornecedor.DadosCadastroFornecedorDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.fornecedor.DadosDetalhamentoFornecedorDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Fornecedor;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.FornecedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository repository;

    @Autowired
    private com.example.nitro_autopecas_sistema.nitro_autopecas.infra.client.BrasilApiClient brasilApiClient;

    @Transactional
    public Fornecedor adicionar(DadosCadastroFornecedorDto dto) {

        String cnpjLimpo = dto.cnpj().replaceAll("[^0-9]", "");

        if (repository.existsByCnpj(cnpjLimpo)) {
            throw new IllegalArgumentException("Fornecedor com este CNPJ já está cadastrado.");
        }

        BrasilApiCnpjDto dadosDaApi = brasilApiClient.buscarDadosPorCnpj(cnpjLimpo);

        Fornecedor fornecedor = new Fornecedor(dadosDaApi);
        fornecedor.setCnpj(cnpjLimpo);

        return repository.save(fornecedor);
    }
    public List<DadosDetalhamentoFornecedorDto> listar(){
        return repository.findAll().stream()
                .map(DadosDetalhamentoFornecedorDto::new)
                .toList();
    }
    @Transactional
    public void inativar(Long id){
        var fornecedorBanco = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado."));
        fornecedorBanco.setAtivo(false);
    }
}
