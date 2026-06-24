package com.example.nitro_autopecas_sistema.nitro_autopecas.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemErro {
    private String mensagem;
    private Integer status;
}