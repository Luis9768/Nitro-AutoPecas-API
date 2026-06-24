package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.funcionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDate;

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

        @NotBlank(message = "O cargo do funcionário é obrigatório!")
        Long cargoId,

        @NotBlank(message = "O salário do funcionário é obrigatório!")
        BigDecimal salario,
        @NotBlank(message = "A senha é obrigatória!")
        String senha
) {
}
