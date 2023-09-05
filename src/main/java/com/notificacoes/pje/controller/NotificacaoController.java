package com.notificacoes.pje.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notificacoes.pje.model.Notificacao;
import com.notificacoes.pje.repository.NotificaoRepository;

@RestController
public class NotificacaoController {

    @Autowired
    private NotificaoRepository notificacaoRepo;

    @GetMapping(path = "/notificacao")
    public List<Notificacao> listarPessoas() {
        return notificacaoRepo.findAll();
    }
}
