package com.example.nitro_autopecas_sistema.nitro_autopecas.service;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.funcionarioDto.DadosAtualizarFuncionarioDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.funcionarioDto.DadosCadastroFuncionarioDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.funcionarioDto.DadosDetalhamentoFuncionarioDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.viaCepDto.ViaCepDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.*;
import com.example.nitro_autopecas_sistema.nitro_autopecas.infra.client.ViaCepClient;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.CargoRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.FuncionarioRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.UsuarioLoginRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository repository;
    @Autowired
    private UsuarioLoginRepository usuarioLoginRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ViaCepClient viaCepClient;
    @Autowired
    private CargoRepository cargoRepository;

    @Transactional
    public DadosDetalhamentoFuncionarioDto adicionar(DadosCadastroFuncionarioDto dto) {
        if (repository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado com este CPF/Dado!");
        }
        if (usuarioLoginRepository.existsByLogin(dto.email())) {
            throw new IllegalArgumentException("Já existe um usuário de acesso cadastrado com este E-mail!");
        }
        if (repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado com este Email/Dado!");
        }
        if (repository.existsByContato(dto.contato())) {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado com este Contato!");
        }

        String cepLimpo = dto.cep().replaceAll("[^0-9]", "");

        ViaCepDto enderecoApi = viaCepClient.buscarEnderecoPorCep(cepLimpo);
        if(enderecoApi == null){
            throw new IllegalArgumentException("CEP não existe!");
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
        funcionario.setCep(cepLimpo);
        funcionario.setLogradouro(enderecoApi.logradouro());
        funcionario.setBairro(enderecoApi.bairro());
        funcionario.setCidade(enderecoApi.localidade());
        funcionario.setEstado(enderecoApi.uf());
        funcionario.setNumero(dto.numero());
        funcionario.setComplemento(dto.complementoDaCasa() != null ? dto.complementoDaCasa() : enderecoApi.complemento());
        funcionario.setUsuario(usuario);
        funcionario.setAtivo(true);
        repository.save(funcionario);
        return new DadosDetalhamentoFuncionarioDto(funcionario);
    }

    public List<DadosDetalhamentoFuncionarioDto> listar(Usuario usuarioLogado) {
        boolean ehAdmin = usuarioLogado.getPerfil() == Perfil.FUNCIONARIO;
        if (!ehAdmin) {
            throw new IllegalArgumentException("Você não tem permissão para ver os usuários!");
        }
        return repository.findAll().stream()
                .map(DadosDetalhamentoFuncionarioDto::new)
                .toList();
    }

    @Transactional

    public DadosDetalhamentoFuncionarioDto atualizar(Long id, DadosAtualizarFuncionarioDto dto, Usuario usuarioLogado) {
        Funcionario funcionarioBanco = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente para atualizar não encontrado!"));
        boolean ehDono = funcionarioBanco.getUsuario().getId().equals(usuarioLogado.getId());
        if (!ehDono) {
            throw new IllegalArgumentException("Você não pode alterar os dados de outra pessoa!");
        }
        if (dto.cpf() != null && !dto.cpf().isBlank()) {
            Optional<Funcionario> existeCpf = repository.findByCpf(dto.cpf());
            if (existeCpf.isPresent() && !existeCpf.get().getId().equals(funcionarioBanco.getId())) {
                throw new IllegalArgumentException("Erro: Este CPF já pertence a outro funcionario no sistema!");
            }
        }
        if (dto.nome() != null && !dto.nome().isBlank()) {
            funcionarioBanco.setNome(dto.nome());
        }
        if (dto.contato() != null && !dto.contato().isBlank()) {
            funcionarioBanco.setContato(dto.contato());
        }
        if (dto.cpf() != null && !dto.cpf().isBlank()) {
            funcionarioBanco.setCpf(dto.cpf());
        }
        if (dto.email() != null && !dto.email().isBlank()) {
            funcionarioBanco.setEmail(dto.email());
            funcionarioBanco.getUsuario().setLogin(dto.email());
        }
        if (dto.senha() != null && !dto.senha().isBlank()) {
            String senha = passwordEncoder.encode(dto.senha());
            funcionarioBanco.getUsuario().setSenha(senha);
        }
        funcionarioBanco = repository.save(funcionarioBanco);

        return new DadosDetalhamentoFuncionarioDto(funcionarioBanco);
    }

    @Transactional
    public void excluirUsuarioId(Long idAlvo, Usuario usuarioLogado) {

        Funcionario funcionarioAlvo = repository.findById(idAlvo)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado!"));

        Usuario usuarioAlvo = funcionarioAlvo.getUsuario();

        boolean ehAdmin = usuarioLogado.getPerfil() == Perfil.FUNCIONARIO;
        boolean ehDono = usuarioAlvo.getId().equals(usuarioLogado.getId());

        if (!ehAdmin && !ehDono) {
            throw new IllegalArgumentException("Você não tem permissão para deletar este cliente!");
        }
        funcionarioAlvo.setAtivo(false);
        usuarioAlvo.setAtivo(false);

        repository.save(funcionarioAlvo);
        usuarioLoginRepository.save(usuarioAlvo);

    }
    public List<DadosDetalhamentoFuncionarioDto> pesquisarPorNome(String nome, Usuario usuarioLogado) {
        if (usuarioLogado.getPerfil() == Perfil.FUNCIONARIO) {
            return repository.findByNomeContainingIgnoreCase(nome).stream().map(DadosDetalhamentoFuncionarioDto::new).toList();
        } else {
            throw new IllegalArgumentException("Você não tem permissão para pesquisar usuários!");
        }
    }
    public DadosDetalhamentoFuncionarioDto buscarPorId(Long id, Usuario usuarioLogado){
        boolean ehAdmin = usuarioLogado.getPerfil() == Perfil.FUNCIONARIO;
        Funcionario funcionarioBanco = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionario não encontrado!"));
        boolean ehDono = funcionarioBanco.getUsuario().getId().equals(usuarioLogado.getId());
        if (!ehAdmin && !ehDono) {
            throw new IllegalArgumentException("Você não tem permissão para realizar esta ação!");
        }
        return new DadosDetalhamentoFuncionarioDto(funcionarioBanco);
    }

}



