package com.example.challenge.controller;

import com.example.challenge.dto.DadosAutenticacao;
import com.example.challenge.dto.DadosTokenJWT;
import com.example.challenge.model.Usuario;
import com.example.challenge.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        var autenticactionToken = new UsernamePasswordAuthenticationToken(dados.login(),dados.senha());
        var authentication = manager.authenticate(autenticactionToken);
        var tokenJWT = tokenService.gerarToken((Usuario)(authentication.getPrincipal()));

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
