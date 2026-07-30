package com.example.nitro_autopecas_sistema.nitro_autopecas.service;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.categoriaDto.DadosCadastroCategoriaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.categoriaDto.DadosDetalhamentoCategoriaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Categoria;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.CategoriaRepository;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    CategoriaRepository repository;

    @CacheEvict(value = "categorias", allEntries = true)
    public DadosDetalhamentoCategoriaDto adicionar(DadosCadastroCategoriaDto dto){
        Categoria categoria = new Categoria();
        if(repository.existsByNome(dto.nome())){
            throw new DuplicateRequestException("Já existe uma categoria cadastrada com esse nome.");
        }
        categoria.setNome(dto.nome());
        repository.save(categoria);
        return new DadosDetalhamentoCategoriaDto(categoria);
    }
    @CacheEvict(value = "categorias", allEntries = true)
    public DadosDetalhamentoCategoriaDto atualizar(Long id, DadosCadastroCategoriaDto dto){
        Categoria categoriaBanco = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada."));
        if(categoriaBanco.getNome().equals(dto.nome())){
            throw new DuplicateRequestException("Já existe uma categoria cadastrada com esse nome.");
        }
        categoriaBanco.setNome(dto.nome());
        repository.save(categoriaBanco);
        return new DadosDetalhamentoCategoriaDto(categoriaBanco);
    }
    @Cacheable(value = "categorias")
    public List<DadosDetalhamentoCategoriaDto> listar() {
        return repository.findAll().stream()
                .map(DadosDetalhamentoCategoriaDto::new)
                .toList();
    }
    @CacheEvict(value = "categorias", allEntries = true)
    public void inativar(Long id){
        Categoria categoriaBanco = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada."));
        categoriaBanco.setAtivo(false);
        repository.save(categoriaBanco);
    }
}
