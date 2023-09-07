package com.notificacoes.pje.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notificacoes.pje.model.LoteEnvio;

public interface LoteRepository extends JpaRepository<LoteEnvio, Integer> {
}