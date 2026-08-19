package com.example.nitro_autopecas_sistema.nitro_autopecas.service;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaDto.DadosDetalhamentoPecaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaFornecedorDto.DadosCadastroPecaFornecedorDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaFornecedorDto.DadosDetalhamentoPecaFornecedorDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.PecaFornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PecaFornecedorService {

    @Autowired
    PecaFornecedorRepository repository;

    //@Cacheable(value = "pecas_fornecedor", key = "'listar_todas'")
    public List<DadosDetalhamentoPecaFornecedorDto> listar(){
        return repository.findAll().stream()
                .map(DadosDetalhamentoPecaFornecedorDto::new)
                .toList();
    }


}
