package com.notificacoes.pje.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notificacoes.pje.enums.StatusNotificacao;
import com.notificacoes.pje.model.Notificacao;
import com.notificacoes.pje.repository.NotificaoRepository;

@RestController
@RequestMapping(path = "/integracao")
public class IntegracaoController {

    @Autowired
    private NotificaoRepository notificacaoRepo;

    @GetMapping(path = "/ecartas")
    public List<Notificacao> listarECartas() {
        var notificacoes = notificacaoRepo.FindByStatus(StatusNotificacao.Submetida);
        if (notificacoes.size() < 10) {
            notificacoes.clear();
        }

        for (Notificacao notificacao : notificacoes) {
            notificacao.setDataEnvio(java.time.LocalDateTime.now());
            notificacao.setStatus(StatusNotificacao.Enviada);
        }
        notificacaoRepo.saveAll(notificacoes);
        return notificacoes;
    }
}
