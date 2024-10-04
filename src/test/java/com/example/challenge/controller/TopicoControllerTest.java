package com.example.challenge.controller;

import com.example.challenge.dto.DadosTopico;
import com.example.challenge.model.Curso;
import com.example.challenge.model.Topico;
import com.example.challenge.model.Usuario;
import com.example.challenge.repository.UsuarioRepository;
import com.example.challenge.service.TokenService;
import com.example.challenge.service.TopicoService;
import com.example.challenge.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TopicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosTopico> dadosTopicoJson;

    @MockBean
    private TopicoService topicoService;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private TokenService tokenService;

    @BeforeEach
    void setup() {
        var usuario = new Usuario(1L, "usuarioTeste", "usuario@teste.com", "usuarioTeste", "senha", List.of("ROLE_USER"));
        when(tokenService.getSubject(anyString())).thenReturn(usuario.getLogin());
        when(usuarioService.findByLogin(usuario.getLogin())).thenReturn(usuario);
    }

    @Test
    @DisplayName("Deveria devolver o código 400 quando informações estão inválidas")
    @WithMockUser
    void criarTopico_cenario1() throws Exception {
        var response = mvc.perform(post("/topicos"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver o código 201 quando informações estão válidas")
    @WithMockUser(username = "usuarioTeste", roles = {"USER"})
    void criarTopico_cenario2() throws Exception {
        var dadosTopico = new DadosTopico("Título do Tópico", "Mensagem do Tópico", 1L);
        var usuario = new Usuario(1L, "usuarioTeste", "usuario@teste.com", "usuarioTeste", "senha", List.of("ROLE_USER"));
        var curso = new Curso(1L, "Curso Teste", "Descrição Teste");
        var topicoRetornado = new Topico(dadosTopico, usuario, curso);

        String token = "mocked-token-jwt";

        when(usuarioService.obterUsuarioLogado(token)).thenReturn(usuario);
        when(topicoService.criarTopico(any(DadosTopico.class), anyString())).thenReturn(topicoRetornado);

        var response = mvc
                .perform(post("/topicos")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosTopicoJson.write(dadosTopico).getJson()))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }
}