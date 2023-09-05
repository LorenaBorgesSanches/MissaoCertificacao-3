package com.notificacoes.pje.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notificacoes.pje.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}