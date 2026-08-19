package com.example.nitro_autopecas_sistema.nitro_autopecas.service;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.metodoPagamentoDto.DadosCadastroMetodoPagamentoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.metodoPagamentoDto.DadosDetalhamentoMetodoPagamentoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.MetodoPagamento;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.MetodoPagamentoRepository;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetodoPagamentoService {

    @Autowired
    MetodoPagamentoRepository repository;

    public DadosDetalhamentoMetodoPagamentoDto adicionar(DadosCadastroMetodoPagamentoDto dto) {
        MetodoPagamento metodoPagamento = new MetodoPagamento();
        if (repository.existsByNome(dto.nome())) {
            throw new DuplicateRequestException("Metodo de pagamento já cadastrado.");
        }
        metodoPagamento.setNome(dto.nome());
        metodoPagamento.setAtivo(true);
        repository.save(metodoPagamento);

        return new DadosDetalhamentoMetodoPagamentoDto(metodoPagamento);
    }

    public DadosDetalhamentoMetodoPagamentoDto atualizar(Integer id, DadosCadastroMetodoPagamentoDto dto) {
        MetodoPagamento metodoPagamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Metodo de pagamento não encontrado."));
        if (metodoPagamento.getNome().equals(dto.nome())) {
            throw new DuplicateRequestException("Já existe um metodo de pagamento cadastrado com esse nome.");
        }
        metodoPagamento.setNome(dto.nome());
        repository.save(metodoPagamento);
        return new DadosDetalhamentoMetodoPagamentoDto(metodoPagamento);
    }

    public void inativar(Integer id) {
        MetodoPagamento metodoPagamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Metodo de pagamento não encontrado."));
        metodoPagamento.setAtivo(false);
        repository.save(metodoPagamento);

    }

    public List<DadosDetalhamentoMetodoPagamentoDto>listar(){
        return repository.findAll().stream()
                .map(DadosDetalhamentoMetodoPagamentoDto::new)
                .toList();
    }
}
