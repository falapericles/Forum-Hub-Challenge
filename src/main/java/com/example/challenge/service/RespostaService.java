package com.example.challenge.service;

import com.example.challenge.dto.DadosResposta;
import com.example.challenge.dto.DetalhesResposta;
import com.example.challenge.dto.DetalhesTopico;
import com.example.challenge.dto.DetalhesTopicoComRespostasPage;
import com.example.challenge.infra.exception.RespostaNaoEncontradaException;
import com.example.challenge.infra.exception.TopicoNaoEncontradoException;
import com.example.challenge.infra.exception.UsuarioSemPermissaoException;
import com.example.challenge.model.Resposta;
import com.example.challenge.model.Usuario;
import com.example.challenge.repository.RespostaRepository;
import com.example.challenge.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RespostaService {

    @Autowired
    private RespostaRepository respostaRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public void deleteByTopicoId(Long id) {
        respostaRepository.deleteByTopicoId(id);
    }

    @Transactional
    public void deleteById(Long id) {
        respostaRepository.deleteById(id);
    }

    @Transactional
    public Resposta criarResposta(Long id, DadosResposta dados,String authorizationHeader){
        var usuario = usuarioService.obterUsuarioLogado(authorizationHeader);
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new TopicoNaoEncontradoException(id));
        var resposta = new Resposta(dados.mensagem(), usuario, topico);

        respostaRepository.save(resposta);
        return resposta;
    }

    public DetalhesTopicoComRespostasPage listarRespostas(Long id, Pageable paginacao){
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new TopicoNaoEncontradoException(id));
        var detalhesTopico = new DetalhesTopico(topico);
        var respostasPaginadas = respostaRepository.findByTopicoId(id, paginacao)
                .map(DetalhesResposta::new);
        return new DetalhesTopicoComRespostasPage(detalhesTopico, respostasPaginadas);
    }

    @Transactional
    public Resposta atualizarResposta(Long id, Long idResposta, DadosResposta dados, String authorizationHeader){
        var usuario = usuarioService.obterUsuarioLogado(authorizationHeader);
        var resposta = respostaRepository.findById(idResposta)
                .orElseThrow(() -> new RespostaNaoEncontradaException(idResposta));
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new TopicoNaoEncontradoException(id));

        if (usuario.getId().equals(resposta.getAutor().getId())) {
            resposta.atualizarDados(dados);
            }
        else if (usuario.getId().equals(topico.getUsuario().getId())) {
        resposta.solucionou(dados);
                }
        else
                {
                throw new UsuarioSemPermissaoException();
            }
        respostaRepository.save(resposta);
        return resposta;
    }

    @Transactional
    public void apagarResposta(Long idResposta, String authorizationHeader){
        Usuario usuario = usuarioService.obterUsuarioLogado(authorizationHeader);
        System.out.println("Usuário encontrado: " + usuario);
        var resposta = respostaRepository.findById(idResposta)
                .orElseThrow(() -> new RespostaNaoEncontradaException(idResposta));
        if (usuario.getId().equals(resposta.getAutor().getId())) {
            deleteById(idResposta);
            }
        else
            {
            throw new UsuarioSemPermissaoException();
        }
    }
}
