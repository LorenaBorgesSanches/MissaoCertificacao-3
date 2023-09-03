package com.notificacoes.pje.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notificacoes.pje.model.Pessoa;
import com.notificacoes.pje.repository.PessoaRepository;

@RestController
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepo;

    @GetMapping(path = "/pessoas")
    public List<Pessoa> listarPessoas() {
        return pessoaRepo.findAll();
    }
}
