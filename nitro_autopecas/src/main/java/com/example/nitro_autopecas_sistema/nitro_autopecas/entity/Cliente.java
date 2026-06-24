package com.example.nitro_autopecas_sistema.nitro_autopecas.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Table(name = "cliente")
@SQLRestriction("ativo = true")
@SQLDelete(sql = "UPDATE cliente SET ativo = false WHERE id = ?")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String nome;


    @Column(length = 11, unique = true, nullable = false)
    private String cpf;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String contato;

    private String email;

    private Boolean ativo = true;

}
