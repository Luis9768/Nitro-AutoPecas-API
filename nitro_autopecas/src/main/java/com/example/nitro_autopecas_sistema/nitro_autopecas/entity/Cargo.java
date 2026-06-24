package com.example.nitro_autopecas_sistema.nitro_autopecas.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "cargos")
@Getter
@Setter
@SQLRestriction("ativo = true")
@SQLDelete(sql = "UPDATE cargos SET ativo = false WHERE id = ?")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    private String descricao;

    private Boolean ativo = true;
}
