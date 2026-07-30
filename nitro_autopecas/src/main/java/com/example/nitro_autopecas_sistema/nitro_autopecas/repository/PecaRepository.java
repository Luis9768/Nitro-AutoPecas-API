package com.example.nitro_autopecas_sistema.nitro_autopecas.repository;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Peca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PecaRepository extends JpaRepository<Peca,Long> {

    Optional<Peca> findBySku(String sku);

    Optional<Peca> findByCodigoFabricante(String codigoFabricante);

    boolean existsBySku(String sku);

    List<Peca> findByNomeContainingIgnoreCase(String name);

    @Query("SELECT p FROM Peca p WHERE p.quantidadeEstoque <= p.quantidadeMinima")
    List<Peca> buscarPecasComEstoqueAbaixoDoMinimo();

    @Query("SELECT p FROM Peca p WHERE p.quantidadeEstoque >= p.quantidadeMaxima")
    List<Peca> buscarPecasComEstoqueAcimaDoMaximo();

    @Query("SELECT p FROM Peca p WHERE " +
            "LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(p.sku) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(p.codigoFabricante) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Peca> buscaGlobal(@Param("termo") String termo);

    List<Peca> findByCategoriaId(Long categoriaId);

    @Query("SELECT p FROM Peca p WHERE p.quantidadeEstoque > p.quantidadeMaxima")
    List<Peca> buscarPecasComEstoqueSuperfaturado();

    @Query("SELECT SUM(p.precoCusto * p.quantidadeEstoque) FROM Peca p")
    BigDecimal calcularValorTotalEstoqueCusto();

    @Query("SELECT SUM(p.precoVenda * p.quantidadeEstoque) FROM Peca p")
    BigDecimal calcularValorTotalEstoqueVenda();

    @Query("SELECT p FROM Peca p WHERE p.quantidadeEstoque = 0")
    List<Peca> buscarPecasEsgotadasAutomatico();

}
