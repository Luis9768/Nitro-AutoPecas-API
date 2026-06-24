package com.example.nitro_autopecas_sistema.nitro_autopecas.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "funcionario")
@SQLRestriction("ativo = true")
@SQLDelete(sql = "UPDATE funcionario SET ativo = false WHERE id = ?")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {
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

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @Column(precision = 10, scale = 2)
    private BigDecimal salario;

    private LocalDate dataAdmissao;

    private Boolean ativo = true;


}
