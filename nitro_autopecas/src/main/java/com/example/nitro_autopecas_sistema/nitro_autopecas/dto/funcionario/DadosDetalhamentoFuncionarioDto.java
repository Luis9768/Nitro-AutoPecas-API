package com.example.nitro_autopecas_sistema.nitro_autopecas.dto.funcionario;

import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Cargo;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Cliente;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Funcionario;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Usuario;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDetalhamentoFuncionarioDto(

        Long id,

        String nome,

        String cpf,

        String contato,

        String email,

        Cargo cargo,

        BigDecimal salario,

        LocalDate dataAdmissao,

        Boolean ativo
        ) {
    public DadosDetalhamentoFuncionarioDto(Funcionario funcionario) {
        this(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getContato(),
                funcionario.getEmail(),
                funcionario.getCargo(),
                funcionario.getSalario(),
                funcionario.getDataAdmissao(),
                funcionario.getAtivo()
        );
    }
}
