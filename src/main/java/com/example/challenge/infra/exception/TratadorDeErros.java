package com.example.challenge.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.challenge.infra.dto.DadosErroValidacao;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class TratadorDeErros {

    // Tratamento para TopicoNaoEncontradoException
    @ExceptionHandler(TopicoNaoEncontradoException.class)
    public ResponseEntity tratarTopicoNaoEncontrado(TopicoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Tratamento para UsuarioSemPermissaoException
    @ExceptionHandler(UsuarioSemPermissaoException.class)
    public ResponseEntity tratarUsuarioSemPermissao(UsuarioSemPermissaoException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(RespostaNaoEncontradaException.class)
    public ResponseEntity<String> tratarRespostaNaoEncontrada(RespostaNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratar404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratar400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new));
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
