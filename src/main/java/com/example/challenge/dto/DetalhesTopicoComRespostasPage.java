package com.example.challenge.dto;

import org.springframework.data.domain.Page;

public record DetalhesTopicoComRespostasPage(
        DetalhesTopico topico,
        Page<DetalhesResposta> respostas
) {
}
