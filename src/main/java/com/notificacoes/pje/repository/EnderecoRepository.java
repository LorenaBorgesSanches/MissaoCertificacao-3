package com.notificacoes.pje.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notificacoes.pje.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    Endereco findByCep (String cep);
}