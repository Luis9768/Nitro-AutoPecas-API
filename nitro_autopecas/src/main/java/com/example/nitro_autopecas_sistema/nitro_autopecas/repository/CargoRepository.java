package com.example.nitro_autopecas_sistema.nitro_autopecas.repository;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Cargo;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    List<Cargo> findByNomeContainingIgnoreCase(String name);
}
