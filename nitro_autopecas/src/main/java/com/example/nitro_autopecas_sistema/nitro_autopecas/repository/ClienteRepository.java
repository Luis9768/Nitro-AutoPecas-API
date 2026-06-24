package com.example.nitro_autopecas_sistema.nitro_autopecas.repository;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCpf(String cpf);
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByUsuarioId(Long id);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByContato(String contato);
    List<Cliente> findByNomeContainingIgnoreCase(String name);

}