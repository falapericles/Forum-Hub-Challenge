package com.example.challenge.service;

import com.example.challenge.dto.DadosListagemTopico;
import com.example.challenge.dto.DadosTopico;
import com.example.challenge.dto.DadosTopicoAtualizacao;
import com.example.challenge.infra.exception.TopicoNaoEncontradoException;
import com.example.challenge.infra.exception.UsuarioSemPermissaoException;
import com.example.challenge.model.Curso;
import com.example.challenge.model.Topico;
import com.example.challenge.repository.CursoRepository;
import com.example.challenge.repository.RespostaRepository;
import com.example.challenge.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private RespostaRepository respostaRepository;

    public Optional<Topico> findById(Long id) {
        return topicoRepository.findById(id);
    }

    @Transactional
    public Topico criarTopico(DadosTopico dados, String authorizationHeader) {
        var usuario = usuarioService.obterUsuarioLogado(authorizationHeader);
        Curso curso = cursoRepository.findById(dados.idCurso())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com ID: " + dados.idCurso()));
        var topico = new Topico(dados, usuario, curso);
        return topicoRepository.save(topico);
    }

    public Page<DadosListagemTopico> listarTopicos(Pageable paginacao) {
        return topicoRepository.findAll(paginacao).map(DadosListagemTopico::new);
    }

    @Transactional
    public Topico atualizarTopico(Long id, DadosTopicoAtualizacao dados, String authorizationHeader) {
        var usuario = usuarioService.obterUsuarioLogado(authorizationHeader);
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new TopicoNaoEncontradoException(id));
        if (!topico.getUsuario().getId().equals(usuario.getId())) {
            throw new UsuarioSemPermissaoException();
        }
        topico.atualizarDados(dados);
        topicoRepository.save(topico);
        return topico;
    }

    @Transactional
    public void deleteById(Long id) {
        topicoRepository.deleteById(id);
    }

    @Transactional
    public void apagarTopico(Long id, String authorizationHeader) {
        var usuario = usuarioService.obterUsuarioLogado(authorizationHeader);
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new TopicoNaoEncontradoException(id));
        if (topico.getUsuario().getId().equals(usuario.getId())) {
            deleteById(id);
            respostaRepository.deleteByTopicoId(id);
        }
        throw new UsuarioSemPermissaoException();
    }
}
