package com.example.nitro_autopecas_sistema.nitro_autopecas.repository;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.StatusVenda;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByFuncionarioId(Long funcionarioId);

    List<Venda> findByClienteId(Long clienteId);

    List<Venda> findByValorTotalGreaterThan(BigDecimal valor);

    List<Venda> findByValorTotalLessThan(BigDecimal valor);

    List<Venda> findByDataVendaBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Venda> findByStatus(StatusVenda status);

    List<Venda> findByStatusAndFuncionario(StatusVenda Venda, Long funcionarioId);

    List<Venda> findByStatusAndDataVendaBetween(StatusVenda status, LocalDateTime inicio, LocalDateTime fim);

}