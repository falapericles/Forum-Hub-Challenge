package com.example.challenge.dto;

import com.example.challenge.model.Usuario;

public record DetalhesUsuario(
        String nome,
        String email,
        String username
) {
    public DetalhesUsuario(Usuario usuario){
        this(usuario.getNome(), usuario.getEmail(), usuario.getLogin());
    }
}
