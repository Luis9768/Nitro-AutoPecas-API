package com.example.nitro_autopecas_sistema.nitro_autopecas.service;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pagamentoDto.DadosCriarPagamentoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pagamentoDto.DadosDetalhamentoPagamentoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.*;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.MetodoPagamentoRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.PagamentoRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository repository;
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private MetodoPagamentoRepository metodoPagamentoRepository;

    public DadosDetalhamentoPagamentoDto criarPagamento(DadosCriarPagamentoDto dto) {
        Pagamento pagamento = new Pagamento();

        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setParcelas(dto.parcelas() != null ? dto.parcelas() : 1);

        Venda venda = vendaRepository.findById(dto.vendaId())
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com o ID: " + dto.vendaId()));
        pagamento.setVenda(venda);

        MetodoPagamento metodo = metodoPagamentoRepository.findById(dto.metodoPagamentoId())
                .orElseThrow(() -> new EntityNotFoundException("Método de pagamento não encontrado com o ID: " + dto.metodoPagamentoId()));
        pagamento.setMetodoPagamento(metodo);

        if (metodo.getNome().equalsIgnoreCase("Dinheiro") || metodo.getNome().equalsIgnoreCase("Espécie")) {
            pagamento.setStatus(StatusPagamento.APROVADO);
        } else {
            pagamento.setStatus(StatusPagamento.PENDENTE);
        }

        pagamento.setValorPago(dto.valorPago());
        pagamento.setDesconto(dto.desconto() != null ? dto.desconto() : BigDecimal.ZERO);
        pagamento.aplicarDesconto();

        repository.save(pagamento);
        return new DadosDetalhamentoPagamentoDto(pagamento);
    }
    public DadosDetalhamentoPagamentoDto estornarPagamento(Long id){
        Pagamento  pagamento = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado."));
        Venda venda = pagamento.getVenda();

        pagamento.setStatus(StatusPagamento.ESTORNADO);
        venda.setStatus(StatusVenda.CANCELADA);

        vendaRepository.save(venda);
        repository.save(pagamento);

        return new DadosDetalhamentoPagamentoDto(pagamento);
    }
    public List<DadosDetalhamentoPagamentoDto> buscarPagamentosPorVendaId(Long id) {
        List<Pagamento> pagamentos = repository.findByVendaId(id);

        return pagamentos.stream()
                .map(DadosDetalhamentoPagamentoDto::new)
                .toList();
    }
    public List<DadosDetalhamentoPagamentoDto> buscarPagamentosPorStatus(StatusPagamento statusPagamento){
        List<Pagamento> pagamento = repository.findByStatus(statusPagamento);
        return pagamento.stream()
                .map(DadosDetalhamentoPagamentoDto::new)
                .toList();

    }
    public List<DadosDetalhamentoPagamentoDto> buscarPorStatusEMetodoDePagamento(StatusPagamento statusPagamento, MetodoPagamento metodoPagamento){
        List<Pagamento> pagamento = repository.findByStatusAndMetodoPagamento(statusPagamento,metodoPagamento);
        return pagamento.stream()
                .map(DadosDetalhamentoPagamentoDto::new)
                .toList();
    }
    public List<DadosDetalhamentoPagamentoDto> buscarPagamentosPorMetodoPagamento(MetodoPagamento metodoPagamento){
        List<Pagamento> pagamento = repository.findByMetodoPagamento(metodoPagamento);
        return pagamento.stream()
                .map(DadosDetalhamentoPagamentoDto::new)
                .toList();

    }
    public List<DadosDetalhamentoPagamentoDto> buscarPagamentosPorDataPagamento(LocalDateTime data){
        List<Pagamento> pagamento = repository.findByDataPagamento(data);
        return pagamento.stream()
                .map(DadosDetalhamentoPagamentoDto::new)
                .toList();

    }
    public List<DadosDetalhamentoPagamentoDto> buscarPagamentosPorDataPagamentoEntre(LocalDateTime datainicio, LocalDateTime dataFim){
        List<Pagamento> pagamento = repository.findByDataPagamentoBetween(datainicio, dataFim);
        return pagamento.stream()
                .map(DadosDetalhamentoPagamentoDto::new)
                .toList();

    }
    public List<DadosDetalhamentoPagamentoDto> buscarPagamentoPorStatusEDataPagamentoEntre(StatusPagamento statusPagamento,LocalDateTime datainicio, LocalDateTime dataFim){
        List<Pagamento> pagamento = repository.findByStatusAndDataPagamentoBetween(statusPagamento, datainicio, dataFim);
        return pagamento.stream()
                .map(DadosDetalhamentoPagamentoDto::new)
                .toList();

    }
}
