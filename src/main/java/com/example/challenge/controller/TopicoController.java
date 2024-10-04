package com.example.challenge.controller;

import com.example.challenge.dto.*;
import com.example.challenge.model.Topico;
import com.example.challenge.service.TopicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<DetalhesTopico> criarTopico(@RequestBody @Valid DadosTopico dados, UriComponentsBuilder uriBuilder,
                                                      @RequestHeader("Authorization") String authorizationHeader) {
        var topicoCriado = topicoService.criarTopico(dados, authorizationHeader);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topicoCriado.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesTopico(topicoCriado));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listarTopico(@PageableDefault(size = 10, sort = {"dataCriacao"},
                                                                    direction = Sort.Direction.DESC) Pageable paginacao) {
        var page = topicoService.listarTopicos(paginacao);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesTopico> atualizar(@PathVariable Long id,
                                                    @RequestBody @Valid DadosTopicoAtualizacao dados,
                                                    @RequestHeader("Authorization") String authorizationHeader) {
        Topico topicoAtualizado = topicoService.atualizarTopico(id, dados, authorizationHeader);
        return ResponseEntity.ok(new DetalhesTopico(topicoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id,
                                        @RequestHeader("Authorization") String authorizationHeader) {
        topicoService.apagarTopico(id,authorizationHeader);
        return ResponseEntity.noContent().build();
    }
}
