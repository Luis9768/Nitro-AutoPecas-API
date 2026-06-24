package com.example.nitro_autopecas_sistema.nitro_autopecas.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(
                erros.stream()
                        .map(DadosErroValidacao::new)
                        .toList()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MensagemErroPadrao> tratarErroDeDuplicidade(DataIntegrityViolationException ex) {
        MensagemErroPadrao erro = new MensagemErroPadrao(
                HttpStatus.CONFLICT.value(),
                "Erro de integridade: O dado informado já está em uso no sistema."
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MensagemErroPadrao> tratarErroTipoDados(MethodArgumentTypeMismatchException ex) {
        String mensagem = "O parâmetro '" + ex.getName() + "' recebeu um valor inválido: '" + ex.getValue() + "'.";
        MensagemErroPadrao erro = new MensagemErroPadrao(HttpStatus.BAD_REQUEST.value(), mensagem);
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MensagemErroPadrao> tratarArgumentosIlegais(IllegalArgumentException ex) {
        MensagemErroPadrao erro = new MensagemErroPadrao(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensagemErroPadrao> tratarErro500(Exception ex) {
        MensagemErroPadrao erro = new MensagemErroPadrao(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor: " + ex.getLocalizedMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }


    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    private record MensagemErroPadrao(int status, String mensagem) {}
}