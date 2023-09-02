/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.missao.notificacaoPje.jpa.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Lorena Sanches
 */
@Entity
@Table(name = "Notificacao")
@NamedQueries({
    @NamedQuery(name = "Notificacao.findAll", query = "SELECT n FROM Notificacao n"),
    @NamedQuery(name = "Notificacao.findById", query = "SELECT n FROM Notificacao n WHERE n.id = :id"),
    @NamedQuery(name = "Notificacao.findByTipoDocumento", query = "SELECT n FROM Notificacao n WHERE n.tipoDocumento = :tipoDocumento"),
    @NamedQuery(name = "Notificacao.findByDescricao", query = "SELECT n FROM Notificacao n WHERE n.descricao = :descricao"),
    @NamedQuery(name = "Notificacao.findBySituacao", query = "SELECT n FROM Notificacao n WHERE n.situacao = :situacao"),
    @NamedQuery(name = "Notificacao.findByDataSubmissao", query = "SELECT n FROM Notificacao n WHERE n.dataSubmissao = :dataSubmissao"),
    @NamedQuery(name = "Notificacao.findByDataEnvio", query = "SELECT n FROM Notificacao n WHERE n.dataEnvio = :dataEnvio"),
    @NamedQuery(name = "Notificacao.findByTipoEnvio", query = "SELECT n FROM Notificacao n WHERE n.tipoEnvio = :tipoEnvio")})
public class Notificacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "tipoDocumento")
    private String tipoDocumento;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "situacao")
    private Integer situacao;
    @Column(name = "dataSubmissao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSubmissao;
    @Column(name = "dataEnvio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvio;
    @Column(name = "tipoEnvio")
    private Integer tipoEnvio;
    @JoinColumn(name = "pessoa", referencedColumnName = "documento")
    @ManyToOne(optional = false)
    private Pessoa pessoa;

    public Notificacao() {
    }

    public Notificacao(Integer id) {
        this.id = id;
    }

    public Notificacao(Integer id, String tipoDocumento) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getSituacao() {
        return situacao;
    }

    public void setSituacao(Integer situacao) {
        this.situacao = situacao;
    }

    public Date getDataSubmissao() {
        return dataSubmissao;
    }

    public void setDataSubmissao(Date dataSubmissao) {
        this.dataSubmissao = dataSubmissao;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Integer getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(Integer tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificacao)) {
            return false;
        }
        Notificacao other = (Notificacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.missao.notificacaoPje.model.Notificacao[ id=" + id + " ]";
    }
    
}
