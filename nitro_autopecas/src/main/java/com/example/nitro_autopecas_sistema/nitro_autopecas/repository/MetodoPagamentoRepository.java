package com.example.nitro_autopecas_sistema.nitro_autopecas.repository;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Categoria;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.MetodoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamento,Integer> {
    List<MetodoPagamento> findByNomeContainingIgnoreCase(String name);
    Optional<MetodoPagamento> findById(Long id);
    boolean existsByNome(String nome);
}
