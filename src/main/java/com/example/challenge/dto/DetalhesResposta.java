package com.example.challenge.dto;

import com.example.challenge.model.Resposta;

public record DetalhesResposta(
        String mensagem,
        String autor,
        Long id
) {
public DetalhesResposta(Resposta dados){
    this(dados.getMensagem(),dados.getAutor().getNome(), dados.getId());
}
}
