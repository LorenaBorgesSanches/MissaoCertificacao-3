package com.notificacoes.pje.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Integer id;
    
    @NotNull
    @Column(nullable = false, unique = true)
    @Getter @Setter private String cep;

    @Getter @Setter private String logradouro;
    @Getter @Setter private String bairro;
    @Getter @Setter private String localidade;
    @Getter @Setter private String uf;
}
