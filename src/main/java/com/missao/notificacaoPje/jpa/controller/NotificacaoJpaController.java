/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.missao.notificacaoPje.jpa.controller;

import com.missao.notificacaoPje.jpa.controller.exceptions.NonexistentEntityException;
import com.missao.notificacaoPje.jpa.model.Notificacao;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.missao.notificacaoPje.jpa.model.Pessoa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Lorena Sanches
 */
public class NotificacaoJpaController implements Serializable {

    public NotificacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notificacao notificacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa pessoa = notificacao.getPessoa();
            if (pessoa != null) {
                pessoa = em.getReference(pessoa.getClass(), pessoa.getDocumento());
                notificacao.setPessoa(pessoa);
            }
            em.persist(notificacao);
            if (pessoa != null) {
                pessoa.getNotificacaoCollection().add(notificacao);
                pessoa = em.merge(pessoa);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notificacao notificacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notificacao persistentNotificacao = em.find(Notificacao.class, notificacao.getId());
            Pessoa pessoaOld = persistentNotificacao.getPessoa();
            Pessoa pessoaNew = notificacao.getPessoa();
            if (pessoaNew != null) {
                pessoaNew = em.getReference(pessoaNew.getClass(), pessoaNew.getDocumento());
                notificacao.setPessoa(pessoaNew);
            }
            notificacao = em.merge(notificacao);
            if (pessoaOld != null && !pessoaOld.equals(pessoaNew)) {
                pessoaOld.getNotificacaoCollection().remove(notificacao);
                pessoaOld = em.merge(pessoaOld);
            }
            if (pessoaNew != null && !pessoaNew.equals(pessoaOld)) {
                pessoaNew.getNotificacaoCollection().add(notificacao);
                pessoaNew = em.merge(pessoaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = notificacao.getId();
                if (findNotificacao(id) == null) {
                    throw new NonexistentEntityException("The notificacao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notificacao notificacao;
            try {
                notificacao = em.getReference(Notificacao.class, id);
                notificacao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notificacao with id " + id + " no longer exists.", enfe);
            }
            Pessoa pessoa = notificacao.getPessoa();
            if (pessoa != null) {
                pessoa.getNotificacaoCollection().remove(notificacao);
                pessoa = em.merge(pessoa);
            }
            em.remove(notificacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Notificacao> findNotificacaoEntities() {
        return findNotificacaoEntities(true, -1, -1);
    }

    public List<Notificacao> findNotificacaoEntities(int maxResults, int firstResult) {
        return findNotificacaoEntities(false, maxResults, firstResult);
    }

    private List<Notificacao> findNotificacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notificacao.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Notificacao findNotificacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notificacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notificacao> rt = cq.from(Notificacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
