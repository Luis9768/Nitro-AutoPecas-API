package com.example.nitro_autopecas_sistema.nitro_autopecas.service;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cliente.DadosAtualizarClienteDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cliente.DadosCadastroClienteDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cliente.DadosDetalhamentoClienteDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.viaCepDto.ViaCepDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Cliente;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Perfil;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Usuario;
import com.example.nitro_autopecas_sistema.nitro_autopecas.infra.client.ViaCepClient;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.ClienteRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.UsuarioLoginRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    @Autowired
    private UsuarioLoginRepository usuarioLoginRepository;
    @Autowired
    private ViaCepClient viaCepClient;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public DadosDetalhamentoClienteDto adicionarCliente(DadosCadastroClienteDto dto){

        if (repository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF/Dado!");
        }
        if (repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este Email/Dado!");
        }
        if (repository.existsByContato(dto.contato())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este Contato!");
        }

        String cepLimpo = dto.cep().replaceAll("[^0-9]", "");

        ViaCepDto enderecoApi = viaCepClient.buscarEnderecoPorCep(cepLimpo);
        if(enderecoApi == null){
            throw new IllegalArgumentException("CEP não existe!");
        }

        Cliente cliente = new Cliente();
        Usuario usuario= new Usuario();

        usuario.setLogin(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setPerfil(Perfil.CLIENTE);
        usuario.setAtivo(true);
        usuarioLoginRepository.save(usuario);

        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setCpf(dto.cpf());
        cliente.setContato(dto.contato());
        cliente.setCep(cepLimpo);
        cliente.setLogradouro(enderecoApi.logradouro());
        cliente.setBairro(enderecoApi.bairro());
        cliente.setCidade(enderecoApi.localidade());
        cliente.setEstado(enderecoApi.uf());
        cliente.setNumero(dto.numero());
        cliente.setComplemento(dto.complementoDaCasa() != null ? dto.complementoDaCasa() : enderecoApi.complemento());
        cliente.setUsuario(usuario);
        cliente.setAtivo(true);

        repository.save(cliente);
        return new DadosDetalhamentoClienteDto(cliente);
    }
    @Transactional
    public DadosDetalhamentoClienteDto atualizar(Long id, DadosAtualizarClienteDto dto, Usuario usuarioLogado) {
        Cliente clienteAntigo = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente para atualizar não encontrado!"));

        boolean ehDono = clienteAntigo.getUsuario().getId().equals(usuarioLogado.getId());
        if (!ehDono) {
            throw new IllegalArgumentException("Você não pode alterar os dados de outra pessoa!");
        }

        if (dto.cpf() != null && !dto.cpf().isBlank()) {
            Optional<Cliente> existeCpf = repository.findByCpf(dto.cpf());
            if (existeCpf.isPresent() && !existeCpf.get().getId().equals(clienteAntigo.getId())) {
                throw new IllegalArgumentException("Erro: Este CPF já pertence a outro cliente no sistema!");
            }
            clienteAntigo.setCpf(dto.cpf());
        }

        if (dto.cep() != null && !dto.cep().isBlank()) {
            String cepLimpo = dto.cep().replaceAll("[^0-9]", "");
            ViaCepDto enderecoApi = viaCepClient.buscarEnderecoPorCep(cepLimpo);

            if(enderecoApi == null || enderecoApi.cep() == null){
                throw new IllegalArgumentException("CEP não existe!");
            }

            clienteAntigo.setCep(cepLimpo);
            clienteAntigo.setLogradouro(enderecoApi.logradouro());
            clienteAntigo.setBairro(enderecoApi.bairro());
            clienteAntigo.setCidade(enderecoApi.localidade());
            clienteAntigo.setEstado(enderecoApi.uf());
        }

        if (dto.numeroResidencia() != null && !dto.numeroResidencia().isBlank()) {
            clienteAntigo.setNumero(dto.numeroResidencia());
        }
        if (dto.complemento() != null) {
            clienteAntigo.setComplemento(dto.complemento());
        }
        if (dto.nome() != null && !dto.nome().isBlank()) {
            clienteAntigo.setNome(dto.nome());
        }
        if (dto.contato() != null && !dto.contato().isBlank()) {
            clienteAntigo.setContato(dto.contato());
        }
        if (dto.email() != null && !dto.email().isBlank()) {
            if(repository.existsByEmail(dto.email())){
                throw new IllegalArgumentException("Já existe um usuário utilizando este e-mail");
            }
            clienteAntigo.setEmail(dto.email());
            clienteAntigo.getUsuario().setLogin(dto.email());
        }
        if (dto.senha() != null && !dto.senha().isBlank()) {
            String senha = passwordEncoder.encode(dto.senha());
            clienteAntigo.getUsuario().setSenha(senha);
        }

        clienteAntigo = repository.save(clienteAntigo);

        return new DadosDetalhamentoClienteDto(clienteAntigo);
    }
    @Transactional
    public void excluirUsuarioId(Long idAlvo, Usuario usuarioLogado) {

        Cliente clienteAlvo = repository.findById(idAlvo)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado!"));

        Usuario usuarioAlvo = clienteAlvo.getUsuario();
/*
        boolean ehAdmin = usuarioLogado.getPerfil() == Perfil.FUNCIONARIO;
        boolean ehDono = usuarioAlvo.getId().equals(usuarioLogado.getId());

        if (!ehAdmin && !ehDono) {
            throw new IllegalArgumentException("Você não tem permissão para deletar este cliente!");
        }
        */

        clienteAlvo.setAtivo(false);
        usuarioAlvo.setAtivo(false);

        repository.save(clienteAlvo);
        usuarioLoginRepository.save(usuarioAlvo);
    }
    public List<DadosDetalhamentoClienteDto> listarUsuarios(Usuario usuarioLogado) {
        boolean ehAdmin = usuarioLogado.getPerfil() == Perfil.FUNCIONARIO;
        if (!ehAdmin) {
            throw new IllegalArgumentException("Você não tem permissão para ver os usuários!");
        }
        return repository.findAll().stream()
                .map(DadosDetalhamentoClienteDto::new)
                .toList();
    }
    public List<DadosDetalhamentoClienteDto> pesquisarPorNome(String nome, Usuario usuarioLogado) {
        if (usuarioLogado.getPerfil() == Perfil.FUNCIONARIO) {
            return repository.findByNomeContainingIgnoreCase(nome).stream().map(DadosDetalhamentoClienteDto::new).toList();
        } else {
            throw new IllegalArgumentException("Você não tem permissão para pesquisar usuários!");
        }
    }
}