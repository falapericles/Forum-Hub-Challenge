package com.example.challenge.repository;

import com.example.challenge.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email); // Alterado para retornar Optional<Usuario>
    UserDetails findByLogin(String login);
}


