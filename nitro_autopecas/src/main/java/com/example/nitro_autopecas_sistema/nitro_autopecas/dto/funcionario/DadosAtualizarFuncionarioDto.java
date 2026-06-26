package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.funcionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

public record DadosAtualizarFuncionarioDto(
        String nome,

        @CPF(message = "Formato de CPF inválido!")
        String cpf,

        String contato,

        @Email(message = "O e-mail deve ser válido!")
        String email,

        Long cargoId,

        @Positive(message = "O salário deve ser maior que zero")
        BigDecimal salario,

        String senha
) {
}
