package com.example.nitro_autopecas_sistema.nitro_autopecas.repository;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.MetodoPagamento;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Pagamento;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento,Long> {
    List<Pagamento> findByVendaId(Long vendaId);
    List<Pagamento> findByStatus(StatusPagamento status);
    List<Pagamento> findByStatusAndMetodoPagamento(StatusPagamento status, MetodoPagamento metodo);
    List<Pagamento> findByMetodoPagamento(MetodoPagamento metodo);
    List<Pagamento> findByDataPagamento(LocalDateTime data);
    List<Pagamento> findByDataPagamentoBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Pagamento> findByStatusAndDataPagamentoBetween(StatusPagamento status, LocalDateTime inicio, LocalDateTime fim);
    @Query("SELECT SUM(p.valorPago) FROM Pagamento p WHERE p.status = 'APROVADO' AND p.dataPagamento BETWEEN :inicio AND :fim")
    BigDecimal somarTotalFaturadoNoPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
