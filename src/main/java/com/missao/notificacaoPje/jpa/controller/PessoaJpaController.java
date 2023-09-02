/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.missao.notificacaoPje.jpa.controller;

import com.missao.notificacaoPje.jpa.controller.exceptions.IllegalOrphanException;
import com.missao.notificacaoPje.jpa.controller.exceptions.NonexistentEntityException;
import com.missao.notificacaoPje.jpa.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.missao.notificacaoPje.jpa.model.Endereco;
import com.missao.notificacaoPje.jpa.model.Notificacao;
import com.missao.notificacaoPje.jpa.model.Pessoa;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Lorena Sanches
 */
public class PessoaJpaController implements Serializable {

    public PessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pessoa pessoa) throws PreexistingEntityException, Exception {
        if (pessoa.getNotificacaoCollection() == null) {
            pessoa.setNotificacaoCollection(new ArrayList<Notificacao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereco cep = pessoa.getCep();
            if (cep != null) {
                cep = em.getReference(cep.getClass(), cep.getCep());
                pessoa.setCep(cep);
            }
            Collection<Notificacao> attachedNotificacaoCollection = new ArrayList<Notificacao>();
            for (Notificacao notificacaoCollectionNotificacaoToAttach : pessoa.getNotificacaoCollection()) {
                notificacaoCollectionNotificacaoToAttach = em.getReference(notificacaoCollectionNotificacaoToAttach.getClass(), notificacaoCollectionNotificacaoToAttach.getId());
                attachedNotificacaoCollection.add(notificacaoCollectionNotificacaoToAttach);
            }
            pessoa.setNotificacaoCollection(attachedNotificacaoCollection);
            em.persist(pessoa);
            if (cep != null) {
                cep.getPessoaCollection().add(pessoa);
                cep = em.merge(cep);
            }
            for (Notificacao notificacaoCollectionNotificacao : pessoa.getNotificacaoCollection()) {
                Pessoa oldPessoaOfNotificacaoCollectionNotificacao = notificacaoCollectionNotificacao.getPessoa();
                notificacaoCollectionNotificacao.setPessoa(pessoa);
                notificacaoCollectionNotificacao = em.merge(notificacaoCollectionNotificacao);
                if (oldPessoaOfNotificacaoCollectionNotificacao != null) {
                    oldPessoaOfNotificacaoCollectionNotificacao.getNotificacaoCollection().remove(notificacaoCollectionNotificacao);
                    oldPessoaOfNotificacaoCollectionNotificacao = em.merge(oldPessoaOfNotificacaoCollectionNotificacao);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPessoa(pessoa.getDocumento()) != null) {
                throw new PreexistingEntityException("Pessoa " + pessoa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pessoa pessoa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa persistentPessoa = em.find(Pessoa.class, pessoa.getDocumento());
            Endereco cepOld = persistentPessoa.getCep();
            Endereco cepNew = pessoa.getCep();
            Collection<Notificacao> notificacaoCollectionOld = persistentPessoa.getNotificacaoCollection();
            Collection<Notificacao> notificacaoCollectionNew = pessoa.getNotificacaoCollection();
            List<String> illegalOrphanMessages = null;
            for (Notificacao notificacaoCollectionOldNotificacao : notificacaoCollectionOld) {
                if (!notificacaoCollectionNew.contains(notificacaoCollectionOldNotificacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notificacao " + notificacaoCollectionOldNotificacao + " since its pessoa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cepNew != null) {
                cepNew = em.getReference(cepNew.getClass(), cepNew.getCep());
                pessoa.setCep(cepNew);
            }
            Collection<Notificacao> attachedNotificacaoCollectionNew = new ArrayList<Notificacao>();
            for (Notificacao notificacaoCollectionNewNotificacaoToAttach : notificacaoCollectionNew) {
                notificacaoCollectionNewNotificacaoToAttach = em.getReference(notificacaoCollectionNewNotificacaoToAttach.getClass(), notificacaoCollectionNewNotificacaoToAttach.getId());
                attachedNotificacaoCollectionNew.add(notificacaoCollectionNewNotificacaoToAttach);
            }
            notificacaoCollectionNew = attachedNotificacaoCollectionNew;
            pessoa.setNotificacaoCollection(notificacaoCollectionNew);
            pessoa = em.merge(pessoa);
            if (cepOld != null && !cepOld.equals(cepNew)) {
                cepOld.getPessoaCollection().remove(pessoa);
                cepOld = em.merge(cepOld);
            }
            if (cepNew != null && !cepNew.equals(cepOld)) {
                cepNew.getPessoaCollection().add(pessoa);
                cepNew = em.merge(cepNew);
            }
            for (Notificacao notificacaoCollectionNewNotificacao : notificacaoCollectionNew) {
                if (!notificacaoCollectionOld.contains(notificacaoCollectionNewNotificacao)) {
                    Pessoa oldPessoaOfNotificacaoCollectionNewNotificacao = notificacaoCollectionNewNotificacao.getPessoa();
                    notificacaoCollectionNewNotificacao.setPessoa(pessoa);
                    notificacaoCollectionNewNotificacao = em.merge(notificacaoCollectionNewNotificacao);
                    if (oldPessoaOfNotificacaoCollectionNewNotificacao != null && !oldPessoaOfNotificacaoCollectionNewNotificacao.equals(pessoa)) {
                        oldPessoaOfNotificacaoCollectionNewNotificacao.getNotificacaoCollection().remove(notificacaoCollectionNewNotificacao);
                        oldPessoaOfNotificacaoCollectionNewNotificacao = em.merge(oldPessoaOfNotificacaoCollectionNewNotificacao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pessoa.getDocumento();
                if (findPessoa(id) == null) {
                    throw new NonexistentEntityException("The pessoa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa pessoa;
            try {
                pessoa = em.getReference(Pessoa.class, id);
                pessoa.getDocumento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Notificacao> notificacaoCollectionOrphanCheck = pessoa.getNotificacaoCollection();
            for (Notificacao notificacaoCollectionOrphanCheckNotificacao : notificacaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pessoa (" + pessoa + ") cannot be destroyed since the Notificacao " + notificacaoCollectionOrphanCheckNotificacao + " in its notificacaoCollection field has a non-nullable pessoa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Endereco cep = pessoa.getCep();
            if (cep != null) {
                cep.getPessoaCollection().remove(pessoa);
                cep = em.merge(cep);
            }
            em.remove(pessoa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pessoa> findPessoaEntities() {
        return findPessoaEntities(true, -1, -1);
    }

    public List<Pessoa> findPessoaEntities(int maxResults, int firstResult) {
        return findPessoaEntities(false, maxResults, firstResult);
    }

    private List<Pessoa> findPessoaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pessoa.class));
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

    public Pessoa findPessoa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pessoa> rt = cq.from(Pessoa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
