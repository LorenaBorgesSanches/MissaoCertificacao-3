package com.notificacoes.pje.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.notificacoes.pje.enums.StatusNotificacao;
import com.notificacoes.pje.model.Notificacao;

public interface NotificaoRepository extends JpaRepository<Notificacao, Integer> {
    @Query(value = "SELECT n" +
                   " FROM Notificacao n" +
                   " INNER JOIN pessoa p" + 
                   " WHERE p.endereco Is Not Null AND n.status = :status")
    List<Notificacao> FindByStatus(StatusNotificacao status);
}