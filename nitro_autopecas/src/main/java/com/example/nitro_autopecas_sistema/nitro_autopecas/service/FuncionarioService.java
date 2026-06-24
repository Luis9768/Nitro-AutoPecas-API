package com.example.nitro_autopecas_sistema.nitro_autopecas.service;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cliente.DadosDetalhamentoClienteDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.funcionario.DadosCadastroFuncionarioDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.funcionario.DadosDetalhamentoFuncionarioDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Cargo;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Funcionario;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Perfil;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Usuario;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.CargoRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.ClienteRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.FuncionarioRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.UsuarioLoginRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository repository;
    @Autowired
    private UsuarioLoginRepository usuarioLoginRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CargoRepository cargoRepository;

    @Transactional
    public DadosDetalhamentoFuncionarioDto adicionar(DadosCadastroFuncionarioDto dto){
        if (repository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF/Dado!");
        }
        if (repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este Email/Dado!");
        }
        if (repository.existsByContato(dto.contato())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este Contato!");
        }
        Cargo cargo = cargoRepository.findById(dto.cargoId()).orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado."));

        Usuario usuario = new Usuario();
        Funcionario funcionario = new Funcionario();

        usuario.setLogin(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setPerfil(Perfil.FUNCIONARIO);
        usuario.setAtivo(true);

        usuarioLoginRepository.save(usuario);

        funcionario.setNome(dto.nome());
        funcionario.setCpf(dto.cpf());
        funcionario.setContato(dto.contato());
        funcionario.setEmail(dto.email());
        funcionario.setCargo(cargo);
        funcionario.setSalario(dto.salario());
        funcionario.setDataAdmissao(LocalDate.now());
        funcionario.setUsuario(usuario);
        funcionario.setAtivo(true);
        repository.save(funcionario);
        return new DadosDetalhamentoFuncionarioDto(funcionario);
    }
}
