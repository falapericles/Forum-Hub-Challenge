package com.example.challenge.model;

import com.example.challenge.dto.DadosTopico;
import com.example.challenge.dto.DadosTopicoAtualizacao;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name="Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String titulo;

    @Column(unique = true, nullable = false)
    private String mensagem;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatusTopico status = StatusTopico.NAO_RESPONDIDO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", referencedColumnName = "id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Resposta> respostas = new ArrayList<>();

    public Topico(DadosTopico dados, Usuario usuario, Curso curso) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.usuario = usuario;
        this.curso = curso;
        this.dataCriacao = LocalDateTime.now(); // Definindo a data de criação como o momento atual
        this.status = StatusTopico.NAO_RESPONDIDO; // Status inicial
    }

    public void atualizarDados(DadosTopicoAtualizacao dados){
        if (dados.mensagem()!=null){
            this.mensagem = dados.mensagem();
        }
        if (dados.titulo()!=null){
            this.titulo = dados.titulo();
        }
        if (dados.status()!=null && (dados.status().equals(StatusTopico.SOLUCIONADO)
                                        || (dados.status().equals(StatusTopico.FECHADO)))){
            this.status = dados.status();
        }
    }

    public void foiRespondido() {
        this.status = StatusTopico.NAO_SOLUCIONADO;
    }

    public void solucionado() {
        this.status = StatusTopico.SOLUCIONADO;
    }
}

