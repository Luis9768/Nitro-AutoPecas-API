package com.example.nitro_autopecas_sistema.nitro_autopecas.entity;


import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.fornecedor.BrasilApiCnpjDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "fornecedores")
@SQLRestriction("ativo = true")
@SQLDelete(sql = "UPDATE fornecedores SET ativo = false WHERE id = ?")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cnpj;

    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    private String telefone;
    private String email;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    private Boolean ativo = true;

    public Fornecedor(BrasilApiCnpjDto dtoBrasilApi) {
        this.cnpj = dtoBrasilApi.cnpj();
        this.razaoSocial = dtoBrasilApi.razaoSocial();
        this.nomeFantasia = dtoBrasilApi.nomeFantasia();
        this.telefone = dtoBrasilApi.telefone();
        this.email = dtoBrasilApi.email();
        this.cep = dtoBrasilApi.cep();
        this.logradouro = dtoBrasilApi.logradouro();
        this.numero = dtoBrasilApi.numero();
        this.complemento = dtoBrasilApi.complemento();
        this.bairro = dtoBrasilApi.bairro();
        this.cidade = dtoBrasilApi.municipio();
        this.estado = dtoBrasilApi.uf();
        this.ativo = true;
    }
}
