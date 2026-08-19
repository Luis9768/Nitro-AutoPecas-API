package com.example.nitro_autopecas_sistema.nitro_autopecas.repository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UsuarioLoginRepository extends JpaRepository<Usuario, UUID> {
    UserDetails findByLogin(String login);
    boolean existsByLogin(String login);
}
