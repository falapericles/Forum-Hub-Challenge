package com.example.challenge.model;

import com.example.challenge.dto.DadosResposta;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Table(name = "respostas")
@Entity(name="Resposta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String mensagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topico_id")
    @NotNull
    private Topico topico;

    @NotNull
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    @NotNull
    private Usuario autor;
    private Boolean solucao = false;

    public Resposta(@Valid String mensagem, Usuario usuario, Topico topico) {
        this.mensagem=mensagem;
        this.topico=topico;
        this.topico.foiRespondido();
        this.autor=usuario;
    }

    public void solucionou(@Valid DadosResposta dados) {
        if (dados.solucao()!=null && dados.solucao()==true) {
            this.solucao = dados.solucao();
            this.topico.solucionado();
        }
    }

    public void atualizarDados(@Valid DadosResposta dados) {
        if (dados.mensagem()!=null){
            this.mensagem = dados.mensagem();
        }
    }
}
