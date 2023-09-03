package com.notificacoes.pje.model;

import java.util.List;

import org.hibernate.annotations.ManyToAny;

import com.notificacoes.pje.enums.TipoLote;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
public class LoteEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

    @NotNull
    @Column(nullable = false)
    @Getter @Setter private TipoLote tipoLote;

    @NotNull
    @Column(nullable = false)
    @Getter @Setter private Integer numLote;

    @ElementCollection
    @CollectionTable(name="lote_notificacoes", joinColumns=@JoinColumn(name="num_lote"))
    @Getter @Setter private List<Notificacao> notificacao;
}
