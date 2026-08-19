package com.example.nitro_autopecas_sistema.nitro_autopecas.repository;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    Optional<Funcionario> findByCpf(String cpf);
    Optional<Funcionario> findByEmail(String email);
    Optional<Funcionario> findByUsuarioId(UUID id);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByContato(String contato);
    List<Funcionario> findByNomeContainingIgnoreCase(String name);
}