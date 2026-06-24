package com.example.nitro_autopecas_sistema.nitro_autopecas.service;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cargoDto.DadosAtualizarCargoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cargoDto.DadosCadastroCargoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cargoDto.DadosDetalhamentoCargoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Cargo;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.CargoRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    @Autowired
    private CargoRepository repository;

    @Transactional
    public DadosDetalhamentoCargoDto adicionar(DadosCadastroCargoDto dto){
        Cargo cargo = new Cargo();
        cargo.setNome(dto.nome());
        cargo.setDescricao(dto.descricao());
        cargo.setAtivo(true);
        repository.save(cargo);
        return new DadosDetalhamentoCargoDto(cargo);
    }
    @Transactional
    public DadosDetalhamentoCargoDto atualizar(Long id, DadosAtualizarCargoDto dto){
        Cargo cargoBanco = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado."));

        if (dto.nome() != null && !dto.nome().trim().isEmpty()) {
            cargoBanco.setNome(dto.nome());
        }
        if (dto.descricao() != null) {
            cargoBanco.setDescricao(dto.descricao());
        }

        repository.save(cargoBanco);
        return new DadosDetalhamentoCargoDto(cargoBanco);
    }
    public List<DadosDetalhamentoCargoDto> listar() {
        return repository.findAll().stream()
                .map(DadosDetalhamentoCargoDto::new)
                .toList();
    }
    @Transactional
    public void desativarCargo(Long id){
        Cargo cargoBanco = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado."));
        repository.delete(cargoBanco);
    }
}
