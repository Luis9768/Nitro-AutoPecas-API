package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.pagamentoDto;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.MetodoPagamento;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Pagamento;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.StatusPagamento;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosDetalhamentoPagamentoDto(
        Long id,
        Long vendaId,
        MetodoPagamento metodoPagamento,
        BigDecimal valorPago,
        BigDecimal desconto,
        Integer parcelas,
        StatusPagamento status,
        LocalDateTime dataPagamento
) {
    public DadosDetalhamentoPagamentoDto(Pagamento pagamento) {
        this(
                pagamento.getId(),
                pagamento.getVenda().getId(), // Pega apenas o ID da venda vinculada
                pagamento.getMetodoPagamento(),
                pagamento.getValorPago(),
                pagamento.getDesconto(),
                pagamento.getParcelas(),
                pagamento.getStatus(),
                pagamento.getDataPagamento()
        );
    }
}