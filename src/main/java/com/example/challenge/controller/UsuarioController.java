package com.example.challenge.controller;

import com.example.challenge.dto.DadosUsuario;
import com.example.challenge.dto.DetalhesUsuario;
import com.example.challenge.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cadastro")
@SecurityRequirement(name = "bearer-key")
    public class UsuarioController {

        @Autowired
        private UsuarioService usuarioService;

        @PostMapping
        public ResponseEntity<DetalhesUsuario> cadastrarUsuario(@RequestBody DadosUsuario usuario) {
            var novoUsuario = usuarioService.cadastrarUsuario(usuario);
            return new ResponseEntity<>(new DetalhesUsuario(novoUsuario), HttpStatus.CREATED);
        }
    }

