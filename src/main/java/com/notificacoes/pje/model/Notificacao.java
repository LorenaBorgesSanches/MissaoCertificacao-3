package com.notificacoes.pje.model;

import java.sql.Date;

import com.notificacoes.pje.enums.StatusNotificacao;
import com.notificacoes.pje.enums.TipoDocumento;
import com.notificacoes.pje.enums.TipoNotificacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pessoa", referencedColumnName = "id", nullable = false)
    @NotBlank
    private Pessoa pessoa;

    @NotBlank
    @Column(nullable = false)
    private String numProcesso;

    @NotBlank
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @NotBlank
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusNotificacao status;

    @Enumerated(EnumType.STRING)
    private TipoNotificacao tipoNotificacao;

    @NotBlank
    @Column(nullable = false)
    private String descricao;

    @NotBlank
    @Column(nullable = false)
    private Date dataCadastro;
    private Date dataSubmissao;
    private Date dataEnvio;
    private Date dateRecebimento;
}
