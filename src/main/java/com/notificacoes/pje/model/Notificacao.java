package com.notificacoes.pje.model;

import java.sql.Date;

import com.notificacoes.pje.enums.StatusNotificacao;
import com.notificacoes.pje.enums.TipoDocumento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

    @ManyToOne
    @JoinColumn(name="pessoa",referencedColumnName="id", nullable = false)
    @NotNull
    @Getter @Setter private Pessoa pessoa;

    @NotNull
    @Column(nullable = false)
    @Getter @Setter private String numProcesso;

    @NotNull
    @Column(nullable = false)
    @Getter @Setter private TipoDocumento tipoDocumento;

    @NotNull
    @Column(nullable = false)
    @Getter @Setter private StatusNotificacao status;

    @NotNull
    @Column(nullable = false)
    @Getter @Setter private String descricao;

    @NotNull
    @Column(nullable = false)
    @Getter @Setter private Date dataCadastro;
    @Getter @Setter private Date dataSubmissao;
    @Getter @Setter private Date dataEnvio;
    @Getter @Setter private Date dateRecebimento;
}
