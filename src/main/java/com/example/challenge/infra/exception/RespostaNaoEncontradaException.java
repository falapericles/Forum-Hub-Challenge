package com.example.challenge.infra.exception;

public class RespostaNaoEncontradaException extends RuntimeException {
    public RespostaNaoEncontradaException(Long idResposta) {
        super("Resposta com id " + idResposta + " não foi encontrada.");
    }
}
