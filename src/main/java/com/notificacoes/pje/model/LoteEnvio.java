package com.notificacoes.pje.model;

import java.util.List;

import com.notificacoes.pje.enums.TipoLote;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LoteEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoLote tipoLote;

    @NotBlank
    @Column(nullable = false)
    private Integer numLote;

    @ElementCollection
    @CollectionTable(name = "lote_notificacoes", joinColumns = @JoinColumn(name = "num_lote"))
    private List<Notificacao> notificacao;
}
