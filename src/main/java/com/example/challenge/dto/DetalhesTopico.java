package com.example.challenge.dto;

import com.example.challenge.model.Topico;
import java.time.LocalDateTime;

public record DetalhesTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        String status,
        String autorNome,
        String cursoNome
) {
    public DetalhesTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus().name(),
                topico.getUsuario().getNome(),
                topico.getCurso().getNome()
        );
    }
}

