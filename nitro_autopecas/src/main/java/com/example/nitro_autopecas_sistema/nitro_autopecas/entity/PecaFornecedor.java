package com.example.nitro_autopecas_sistema.nitro_autopecas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pecas_fornecedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PecaFornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "peca_id", nullable = false)
    private Peca peca;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @Column(name = "codigo_referencia_fornecedor", nullable = false)
    private String codigoReferenciaFornecedor;
}
