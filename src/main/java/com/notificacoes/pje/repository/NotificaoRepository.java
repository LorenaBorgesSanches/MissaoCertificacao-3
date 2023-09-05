package com.notificacoes.pje.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notificacoes.pje.model.Notificacao;

public interface NotificaoRepository extends JpaRepository<Notificacao, Integer> {

}