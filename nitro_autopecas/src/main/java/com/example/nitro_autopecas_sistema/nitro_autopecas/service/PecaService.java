package com.example.nitro_autopecas_sistema.nitro_autopecas.service;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaDto.DadosAtualizarPecaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaDto.DadosCadastroPecaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pecaDto.DadosDetalhamentoPecaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Categoria;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Fornecedor;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Peca;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.PecaFornecedor;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.CategoriaRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.FornecedorRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.PecaFornecedorRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.PecaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PecaService {

    @Autowired
    PecaRepository repository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    private PecaFornecedorRepository pecaFornecedorRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Transactional
    @CacheEvict(value = "pecas", allEntries = true)
    public DadosDetalhamentoPecaDto adicionar(DadosCadastroPecaDto dto) {

        if (repository.existsBySku(dto.sku())) {
            throw new IllegalArgumentException("Já existe uma peça cadastrada com esse SKU.");
        }

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada para o ID informado."));

        Peca peca = new Peca();
        peca.setNome(dto.nome());
        peca.setSku(dto.sku());
        peca.setDescricao(dto.descricao());
        peca.setCodigoFabricante(dto.codigoFabricante());
        peca.setPrecoCusto(dto.precoCusto());
        peca.setPrecoVenda(dto.precoVenda());
        peca.setQuantidadeMaxima(dto.quantidadeMaxima());
        peca.setQuantidadeMinima(dto.quantidadeMinima());
        peca.setQuantidadeEstoque(dto.quantidadeEstoque());
        peca.setCategoria(categoria);
        peca.setAtivo(true);
        Peca pecaSalva = repository.save(peca);
        if (dto.fornecedoresIds() != null && !dto.fornecedoresIds().isEmpty()) {
            for (Long fornecedorId : dto.fornecedoresIds()) {

                Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                        .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado: " + fornecedorId));

                PecaFornecedor pecaFornecedor = new PecaFornecedor();
                pecaFornecedor.setPeca(pecaSalva);
                pecaFornecedor.setFornecedor(fornecedor);
                pecaFornecedor.setCodigoReferenciaFornecedor(dto.codigoFabricante());
                pecaFornecedorRepository.save(pecaFornecedor);
            }
        }
        return new DadosDetalhamentoPecaDto(pecaSalva);
    }
    @Cacheable(value = "pecas", key = "'listar_todas'")
    public List<DadosDetalhamentoPecaDto> listar(){
       return repository.findAll().stream()
                .map(DadosDetalhamentoPecaDto::new)
                .toList();
    }
    @Cacheable(value = "pecas", key = "#sku")
    public DadosDetalhamentoPecaDto buscarPorSku(String sku){
        Peca pecaBanco = repository.findBySku(sku)
                .orElseThrow(() -> new EntityNotFoundException("Peça não encontrada."));
        return new DadosDetalhamentoPecaDto(pecaBanco);
    }
    @Cacheable(value = "pecas", key = "'fab_' + #codigoFabricante")
    public DadosDetalhamentoPecaDto buscarPorCodigoFabricante(String codigoFabricante){
        Peca pecaBanco = repository.findByCodigoFabricante(codigoFabricante)
                .orElseThrow(() -> new EntityNotFoundException("Peça não encontrada."));
        return new DadosDetalhamentoPecaDto(pecaBanco);
    }
    @Cacheable(value = "pecas", key = "'nome_' + #nome")
    public List<DadosDetalhamentoPecaDto> buscarPorNome(String nome){
        return repository.findByNomeContainingIgnoreCase(nome).stream()
                .map(DadosDetalhamentoPecaDto::new)
                .toList();
    }
    @Cacheable(value = "pecas", key = "'estoque_baixo'")
    public List<DadosDetalhamentoPecaDto> buscarPecasComEstoqueAbaixoDoMinimo(){
        return repository.buscarPecasComEstoqueAbaixoDoMinimo().stream()
                .map(DadosDetalhamentoPecaDto::new)
                .toList();
    }
    @Cacheable(value = "pecas", key = "'estoque_alto'")
    public List<DadosDetalhamentoPecaDto> buscarPecasComEstoqueAcimaDoMaximo(){
        return repository.buscarPecasComEstoqueAcimaDoMaximo().stream()
                .map(DadosDetalhamentoPecaDto::new)
                .toList();
    }
    @Cacheable(value = "pecas", key = "#id")
    public DadosDetalhamentoPecaDto buscarPorId(Long id){
        Peca peca = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Peça não encontrada."));
        return new DadosDetalhamentoPecaDto(peca);
    }
    @Transactional
    @CacheEvict(value = "pecas", allEntries = true)
    public void inativar(Long id){
        var pecaBanco = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Peca não encontrada"));
        pecaBanco.setAtivo(false);
    }
    @Transactional
    @CacheEvict(value = "pecas", allEntries = true)
    public DadosDetalhamentoPecaDto atualizar(Long id, DadosAtualizarPecaDto dto) {

        var pecaBanco = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Peça não encontrada"));

        if (dto.nome() != null && !dto.nome().isBlank()) {
            pecaBanco.setNome(dto.nome());
        }
        if (dto.sku() != null && !dto.sku().isBlank()) {
            pecaBanco.setSku(dto.sku());
        }
        if (dto.codigoFabricante() != null && !dto.codigoFabricante().isBlank()) {
            pecaBanco.setCodigoFabricante(dto.codigoFabricante());
        }
        if (dto.descricao() != null && !dto.descricao().isBlank()) {
            pecaBanco.setDescricao(dto.descricao());
        }
        if (dto.precoCusto() != null) {
            pecaBanco.setPrecoCusto(dto.precoCusto());
        }
        if (dto.precoVenda() != null) {
            pecaBanco.setPrecoVenda(dto.precoVenda());
        }
        if (dto.quantidadeEstoque() != null) {
            pecaBanco.setQuantidadeEstoque(dto.quantidadeEstoque());
        }
        if (dto.quantidadeMinima() != null) {
            pecaBanco.setQuantidadeMinima(dto.quantidadeMinima());
        }
        if (dto.quantidadeMaxima() != null) {
            pecaBanco.setQuantidadeMaxima(dto.quantidadeMaxima());
        }
        if (dto.categoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.categoriaId());
            pecaBanco.setCategoria(categoria);
        }
        return new DadosDetalhamentoPecaDto(pecaBanco);
    }
    @Cacheable(value = "pecas", key = "'busca_global'")
    public List<DadosDetalhamentoPecaDto> buscaGlobal(String termo){
        List<Peca> busca = repository.buscaGlobal(termo);
        return busca.stream()
                .map(DadosDetalhamentoPecaDto::new)
                .toList();
    }
    @Cacheable(value = "pecas", key = "'buscar_categoria'")
    public List<DadosDetalhamentoPecaDto> buscarPorCategoria(Long categoriaId){
        List<Peca> busca = repository.findByCategoriaId(categoriaId);
        return busca.stream()
                .map(DadosDetalhamentoPecaDto::new)
                .toList();
    }
    @Cacheable(value = "pecas", key = "'estoque_superfaturado'")
    public List<DadosDetalhamentoPecaDto> buscarPecasComEstoqueSuperfaturado(){
        List<Peca> busca = repository.buscarPecasComEstoqueSuperfaturado();
        return busca.stream()
                .map(DadosDetalhamentoPecaDto::new)
                .toList();
    }
    @Cacheable(value = "pecas", key = "'calcular_valor_estoque_total_custo'")
    public BigDecimal calcularValorTotalEstoqueCusto(){
        return repository.calcularValorTotalEstoqueCusto();
    }
    @Cacheable(value = "pecas", key = "'calcular_valor_estoque_total_venda'")
    public BigDecimal calcularValorTotalEstoqueVenda(){
        return repository.calcularValorTotalEstoqueVenda();
    }
    @Cacheable(value = "pecas", key = "'pecas_esgotadas'")
    public List<DadosDetalhamentoPecaDto> buscarPecasEsgotadas(){
        List<Peca> busca = repository.buscarPecasEsgotadasAutomatico();
        return busca.stream()
                .map(DadosDetalhamentoPecaDto::new)
                .toList();
    }
}