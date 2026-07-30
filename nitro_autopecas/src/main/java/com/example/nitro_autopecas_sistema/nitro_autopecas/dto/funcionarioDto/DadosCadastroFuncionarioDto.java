package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.funcionarioDto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

public record DadosCadastroFuncionarioDto(

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

        @NotNull(message = "O cargo do funcionário é obrigatório!")
        Long cargoId,

        @NotNull(message = "O salário do funcionário é obrigatório!")
        @Positive(message = "O salário deve ser maior que zero")
        BigDecimal salario,

        @NotBlank(message = "A senha é obrigatória!")
        String senha,
        @NotBlank @Pattern(regexp = "\\d{8}", message = "O CEP deve ter 8 dígitos")
        String cep,
        @NotBlank String numero,
        String complementoDaCasa
) {
}
