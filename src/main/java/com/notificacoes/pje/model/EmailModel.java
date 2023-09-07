package com.notificacoes.pje.model;

import java.time.LocalDateTime;

import com.notificacoes.pje.enums.StatusEMail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "email")
@Entity
public class EmailModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String titulo;
    @Column(columnDefinition = "TEXT")
    private String texto;
    private LocalDateTime sendDateEmail;
    private StatusEMail statusEmail;

}
