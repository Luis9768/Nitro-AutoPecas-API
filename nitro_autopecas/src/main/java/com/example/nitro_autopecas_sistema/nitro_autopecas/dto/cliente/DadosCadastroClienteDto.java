package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record DadosCadastroClienteDto(
        @NotBlank(message = "O nome é obrigatório!")
        String nome,

        @NotBlank(message = "O CPF é obrigatório!")
        @CPF(message = "Formato de CPF inválido!")
        String cpf,

        @NotBlank(message = "O contato é obrigatório!")
        String contato,

        @NotBlank(message = "O email é obrigatório!")
        @Email(message = "O e-mail deve ser válido!")
        String email,

        @NotBlank(message = "A senha é obrigatória!")
        String senha,

        @NotBlank @Pattern(regexp = "\\d{8}", message = "O CEP deve ter 8 dígitos")
        String cep,
        @NotBlank String numero,
        String complementoDaCasa
) {
}