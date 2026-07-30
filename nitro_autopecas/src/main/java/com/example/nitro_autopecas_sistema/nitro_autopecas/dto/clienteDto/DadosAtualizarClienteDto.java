package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.clienteDto;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

public record DadosAtualizarClienteDto(
        String nome,

        @CPF(message = "Formato de CPF inválido!")
        String cpf,

        String contato,

        @Email(message = "O e-mail deve ser válido!")
        String email,

        String senha,
        String cep,
        String numeroResidencia,
        String complemento


) {
}
