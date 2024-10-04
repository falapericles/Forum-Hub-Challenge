package com.example.challenge.dto;

import com.example.challenge.model.StatusTopico;
import com.example.challenge.model.Topico;
import java.time.LocalDateTime;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico statusTopico,
        String usuario,
        String curso
) {
    public DadosListagemTopico(Topico topico){
        this(topico.getId(),topico.getTitulo(),
            topico.getMensagem(),
            topico.getDataCriacao(),
            topico.getStatus(),
            topico.getUsuario().getNome(),
            topico.getCurso().getNome());
    }
}
