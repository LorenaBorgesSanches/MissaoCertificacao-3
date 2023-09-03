package com.notificacoes.pje.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Integer id;
    
    @NotNull
    @Column(nullable = false)
    @Getter @Setter private String nome;

    @NotNull
    @Column(nullable = false, updatable = false, unique = true)
    @Getter @Setter private String documento;
    
    @ManyToOne
    @JoinColumn(name = "endereco",referencedColumnName = "id")
    @Getter @Setter private Endereco endereco;
    @Getter @Setter private String numeroEndereco;
    @Getter @Setter private String complemento;
    
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @Getter @Setter private String email;
}
