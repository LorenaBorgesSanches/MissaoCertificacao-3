package com.notificacoes.pje.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notificacoes.pje.dto.PessoaCriacaoDTO;
import com.notificacoes.pje.model.Pessoa;
import com.notificacoes.pje.repository.EnderecoRepository;
import com.notificacoes.pje.repository.PessoaRepository;

@RestController
@RequestMapping(path = "/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepo;
    @Autowired
    private EnderecoRepository enderecoRepo;

    @GetMapping
    public List<Pessoa> listarPessoas() {
        return pessoaRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@RequestBody PessoaCriacaoDTO dto) {
        Pessoa usuario = pessoaRepo.save(dto.converterParaPessoa(enderecoRepo));
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }
}
