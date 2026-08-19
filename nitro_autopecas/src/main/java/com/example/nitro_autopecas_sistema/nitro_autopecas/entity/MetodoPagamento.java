package com.example.nitro_autopecas_sistema.nitro_autopecas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "metodo_pagamento")
@Data
@SQLRestriction("ativo = true")
@SQLDelete(sql = "UPDATE metodo_pagamento SET ativo = false WHERE id = ?")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
public class MetodoPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nome;

    private Boolean ativo = true;
}
