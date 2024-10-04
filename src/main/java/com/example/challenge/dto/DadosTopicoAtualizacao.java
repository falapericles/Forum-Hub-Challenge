package com.example.challenge.dto;

import com.example.challenge.model.StatusTopico;

public record DadosTopicoAtualizacao(
    String titulo,
    String mensagem,
    StatusTopico status
    ){
}
