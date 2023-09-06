package com.notificacoes.pje.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notificacoes.pje.dto.ErrorDTO;
import com.notificacoes.pje.dto.PessoaAtualizacaoDTO;
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
    public List<Pessoa> listar() {
        return pessoaRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@RequestBody PessoaCriacaoDTO dto) {
        Pessoa usuario = pessoaRepo.save(dto.converterParaModel(enderecoRepo));
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> atualizar(@RequestBody PessoaAtualizacaoDTO dto, @PathVariable Integer id) {
        Optional<Pessoa> pessoa = pessoaRepo.findById(id);
        if (!pessoa.isPresent()) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO("Pessoa não encontrada"), HttpStatus.NOT_FOUND);
        }
        pessoaRepo.save(dto.converterParaModel(pessoa.get(), enderecoRepo));

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
