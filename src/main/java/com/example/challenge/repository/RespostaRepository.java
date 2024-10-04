package com.example.challenge.repository;

import com.example.challenge.model.Resposta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    List<Resposta> findByTopicoId(Long topicoId);
    Page<Resposta> findByTopicoId(Long id, Pageable pageable);
    void deleteByTopicoId(Long id);
}

