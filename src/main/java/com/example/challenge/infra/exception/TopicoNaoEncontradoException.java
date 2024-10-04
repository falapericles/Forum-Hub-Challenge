package com.example.challenge.infra.exception;

public class TopicoNaoEncontradoException extends RuntimeException {
    public TopicoNaoEncontradoException(Long id) {
        super("Tópico com id " + id + " não foi encontrado.");
    }
}
