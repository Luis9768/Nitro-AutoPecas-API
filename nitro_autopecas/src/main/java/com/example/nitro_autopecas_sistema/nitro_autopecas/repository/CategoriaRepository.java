package com.example.nitro_autopecas_sistema.nitro_autopecas.repository;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Categoria;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByNomeContainingIgnoreCase(String name);
}
