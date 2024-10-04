package com.example.challenge.controller;

import com.example.challenge.dto.*;
import com.example.challenge.service.RespostaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<DetalhesResposta> responder(@PathVariable Long id, @RequestBody @Valid DadosResposta dados,
                                                      UriComponentsBuilder uriBuilder,
                                                      @RequestHeader("Authorization") String authorizationHeader) {
        var resposta = respostaService.criarResposta(id, dados, authorizationHeader);
        var uri = uriBuilder.path("/topicos/{id}/resposta/{respostaId}").buildAndExpand(id, resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesResposta(resposta));
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhesTopicoComRespostasPage> detalhes(@PathVariable Long id,
                                                                   @PageableDefault(size = 10) Pageable paginacao) {
        var detalhesComRespostas = respostaService.listarRespostas(id, paginacao);
        return ResponseEntity.ok(detalhesComRespostas);
    }

    @PutMapping("/{id}/resposta/{idResposta}")
    @Transactional
    public ResponseEntity<DetalhesResposta> atualizarResposta(@PathVariable Long id, @PathVariable Long idResposta,
                                                              @RequestBody @Valid DadosResposta dados,
                                                              @RequestHeader("Authorization") String authorizationHeader) {
        var respostaAtualizada = respostaService.atualizarResposta(id, idResposta, dados, authorizationHeader);
        return ResponseEntity.ok(new DetalhesResposta(respostaAtualizada));
    }


    @DeleteMapping("/{id}/resposta/{idResposta}")
    @Transactional
    public ResponseEntity<Void> remover(@PathVariable Long idResposta,
                                        @RequestHeader("Authorization") String authorizationHeader) {
        respostaService.apagarResposta(idResposta,authorizationHeader);
        return ResponseEntity.noContent().build();
    }
}