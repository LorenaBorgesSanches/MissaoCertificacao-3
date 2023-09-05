package com.notificacoes.pje.model;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import com.notificacoes.pje.validators.CnpjGroup;
import com.notificacoes.pje.validators.CpfGroup;
import com.notificacoes.pje.validators.PessoaGroupSequenceProvider;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@GroupSequenceProvider(PessoaGroupSequenceProvider.class)
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false, updatable = false, unique = true)
    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
    @Pattern(regexp = "\\d{11}|\\d{14}", message = "Documento deve conter 11(cpf) ou 14(cnpj) digitos")
    private String documento;

    @ManyToOne
    @JoinColumn(name = "endereco", referencedColumnName = "id")
    private Endereco endereco;
    private String numeroEndereco;
    private String complemento;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
}
