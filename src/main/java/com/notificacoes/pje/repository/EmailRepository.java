package com.notificacoes.pje.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notificacoes.pje.model.EmailModel;

public interface EmailRepository extends JpaRepository<EmailModel, Long>{
    
}
