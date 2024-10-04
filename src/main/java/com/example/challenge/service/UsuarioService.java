package com.example.challenge.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.challenge.dto.DadosUsuario;
import com.example.challenge.model.Usuario;
import com.example.challenge.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrarUsuario(DadosUsuario usuario) {
        var novoUsuario = new Usuario(usuario.nome(), usuario.email(), usuario.username(),
                                        usuario.senha());
        return usuarioRepository.save(novoUsuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario obterUsuarioLogado(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Long usuarioLogadoId;
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            usuarioLogadoId = decodedJWT.getClaim("id").asLong(); // Obtenha o ID do usuário do claim
        } catch (Exception e) {
            throw new RuntimeException("Erro ao decodificar o token JWT: " + e.getMessage());
        }

        return usuarioRepository.findById(usuarioLogadoId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + usuarioLogadoId));
    }

    public UserDetails findByLogin(String subject) {
        return usuarioRepository.findByLogin(subject);
    }
}
