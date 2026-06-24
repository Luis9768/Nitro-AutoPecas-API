package com.example.nitro_autopecas_sistema.nitro_autopecas.config.admin;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Perfil;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Usuario;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.UsuarioLoginRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InicializadorAdmin {
    @Bean
    public CommandLineRunner inicializacaoAdmin(UsuarioLoginRepository repository, PasswordEncoder passwordEncoder){
        return args -> {
            if (repository.count() == 0) {
                Usuario funcionario = new Usuario();
                funcionario.setLogin("admin@email.com");
                funcionario.setSenha(passwordEncoder.encode("123456"));
                funcionario.setPerfil(Perfil.FUNCIONARIO);
                funcionario.setAtivo(true);
                repository.save(funcionario);

                System.out.println("==================================================");
                System.out.println("🚀 USUÁRIO MESTRE CRIADO COM SUCESSO!");
                System.out.println("📧 Login: admin@email.com");
                System.out.println("🔑 Senha: 123456");
                System.out.println("==================================================");
            }
        };
    }
}