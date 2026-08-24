package com.example.nitro_autopecas_sistema.nitro_autopecas.service;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pagamentoDto.CriarPagamentoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pagamentoDto.DadosDetalhamentoPagamentoDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.MetodoPagamento;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Pagamento;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.StatusPagamento;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Venda;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.MetodoPagamentoRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.PagamentoRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository repository;
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private MetodoPagamentoRepository metodoPagamentoRepository;

    public DadosDetalhamentoPagamentoDto criarPagamento(CriarPagamentoDto dto){
        Pagamento pagamento = new Pagamento();

        pagamento.setMetodoPagamento(dto.metodoPagamento());
        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setParcelas(dto.parcelas());

        Venda venda = vendaRepository.findById(dto.vendaId())
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com o ID: " + dto.vendaId()));
        pagamento.setVenda(venda);

        MetodoPagamento metodoPagamento = new MetodoPagamento();
        if(metodoPagamento.getNome().equalsIgnoreCase("Dinheiro") ||metodoPagamento.getNome().equalsIgnoreCase("Espécie")){
            pagamento.setStatus(StatusPagamento.APROVADO);
        }else {
            // Para PIX, Cartão, Boleto, etc.
            pagamento.setStatus(StatusPagamento.PENDENTE);
        }
        pagamento.setValorPago(dto.valorPago());
        pagamento.setDesconto(dto.desconto());
        pagamento.aplicarDesconto();

        repository.save(pagamento);
        return new DadosDetalhamentoPagamentoDto(pagamento);
    }
}
