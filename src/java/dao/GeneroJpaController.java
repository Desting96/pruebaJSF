/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidades.Genero;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.GenerohasInfoVideo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author desting
 */
public class GeneroJpaController implements Serializable {

    public GeneroJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Genero genero) throws RollbackFailureException, Exception {
        if (genero.getGenerohasInfoVideoCollection() == null) {
            genero.setGenerohasInfoVideoCollection(new ArrayList<GenerohasInfoVideo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<GenerohasInfoVideo> attachedGenerohasInfoVideoCollection = new ArrayList<GenerohasInfoVideo>();
            for (GenerohasInfoVideo generohasInfoVideoCollectionGenerohasInfoVideoToAttach : genero.getGenerohasInfoVideoCollection()) {
                generohasInfoVideoCollectionGenerohasInfoVideoToAttach = em.getReference(generohasInfoVideoCollectionGenerohasInfoVideoToAttach.getClass(), generohasInfoVideoCollectionGenerohasInfoVideoToAttach.getIdGenerohasInfoVideo());
                attachedGenerohasInfoVideoCollection.add(generohasInfoVideoCollectionGenerohasInfoVideoToAttach);
            }
            genero.setGenerohasInfoVideoCollection(attachedGenerohasInfoVideoCollection);
            em.persist(genero);
            for (GenerohasInfoVideo generohasInfoVideoCollectionGenerohasInfoVideo : genero.getGenerohasInfoVideoCollection()) {
                Genero oldGeneroidGeneroOfGenerohasInfoVideoCollectionGenerohasInfoVideo = generohasInfoVideoCollectionGenerohasInfoVideo.getGeneroidGenero();
                generohasInfoVideoCollectionGenerohasInfoVideo.setGeneroidGenero(genero);
                generohasInfoVideoCollectionGenerohasInfoVideo = em.merge(generohasInfoVideoCollectionGenerohasInfoVideo);
                if (oldGeneroidGeneroOfGenerohasInfoVideoCollectionGenerohasInfoVideo != null) {
                    oldGeneroidGeneroOfGenerohasInfoVideoCollectionGenerohasInfoVideo.getGenerohasInfoVideoCollection().remove(generohasInfoVideoCollectionGenerohasInfoVideo);
                    oldGeneroidGeneroOfGenerohasInfoVideoCollectionGenerohasInfoVideo = em.merge(oldGeneroidGeneroOfGenerohasInfoVideoCollectionGenerohasInfoVideo);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Genero genero) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Genero persistentGenero = em.find(Genero.class, genero.getIdGenero());
            Collection<GenerohasInfoVideo> generohasInfoVideoCollectionOld = persistentGenero.getGenerohasInfoVideoCollection();
            Collection<GenerohasInfoVideo> generohasInfoVideoCollectionNew = genero.getGenerohasInfoVideoCollection();
            List<String> illegalOrphanMessages = null;
            for (GenerohasInfoVideo generohasInfoVideoCollectionOldGenerohasInfoVideo : generohasInfoVideoCollectionOld) {
                if (!generohasInfoVideoCollectionNew.contains(generohasInfoVideoCollectionOldGenerohasInfoVideo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GenerohasInfoVideo " + generohasInfoVideoCollectionOldGenerohasInfoVideo + " since its generoidGenero field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<GenerohasInfoVideo> attachedGenerohasInfoVideoCollectionNew = new ArrayList<GenerohasInfoVideo>();
            for (GenerohasInfoVideo generohasInfoVideoCollectionNewGenerohasInfoVideoToAttach : generohasInfoVideoCollectionNew) {
                generohasInfoVideoCollectionNewGenerohasInfoVideoToAttach = em.getReference(generohasInfoVideoCollectionNewGenerohasInfoVideoToAttach.getClass(), generohasInfoVideoCollectionNewGenerohasInfoVideoToAttach.getIdGenerohasInfoVideo());
                attachedGenerohasInfoVideoCollectionNew.add(generohasInfoVideoCollectionNewGenerohasInfoVideoToAttach);
            }
            generohasInfoVideoCollectionNew = attachedGenerohasInfoVideoCollectionNew;
            genero.setGenerohasInfoVideoCollection(generohasInfoVideoCollectionNew);
            genero = em.merge(genero);
            for (GenerohasInfoVideo generohasInfoVideoCollectionNewGenerohasInfoVideo : generohasInfoVideoCollectionNew) {
                if (!generohasInfoVideoCollectionOld.contains(generohasInfoVideoCollectionNewGenerohasInfoVideo)) {
                    Genero oldGeneroidGeneroOfGenerohasInfoVideoCollectionNewGenerohasInfoVideo = generohasInfoVideoCollectionNewGenerohasInfoVideo.getGeneroidGenero();
                    generohasInfoVideoCollectionNewGenerohasInfoVideo.setGeneroidGenero(genero);
                    generohasInfoVideoCollectionNewGenerohasInfoVideo = em.merge(generohasInfoVideoCollectionNewGenerohasInfoVideo);
                    if (oldGeneroidGeneroOfGenerohasInfoVideoCollectionNewGenerohasInfoVideo != null && !oldGeneroidGeneroOfGenerohasInfoVideoCollectionNewGenerohasInfoVideo.equals(genero)) {
                        oldGeneroidGeneroOfGenerohasInfoVideoCollectionNewGenerohasInfoVideo.getGenerohasInfoVideoCollection().remove(generohasInfoVideoCollectionNewGenerohasInfoVideo);
                        oldGeneroidGeneroOfGenerohasInfoVideoCollectionNewGenerohasInfoVideo = em.merge(oldGeneroidGeneroOfGenerohasInfoVideoCollectionNewGenerohasInfoVideo);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = genero.getIdGenero();
                if (findGenero(id) == null) {
                    throw new NonexistentEntityException("The genero with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Genero genero;
            try {
                genero = em.getReference(Genero.class, id);
                genero.getIdGenero();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The genero with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<GenerohasInfoVideo> generohasInfoVideoCollectionOrphanCheck = genero.getGenerohasInfoVideoCollection();
            for (GenerohasInfoVideo generohasInfoVideoCollectionOrphanCheckGenerohasInfoVideo : generohasInfoVideoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Genero (" + genero + ") cannot be destroyed since the GenerohasInfoVideo " + generohasInfoVideoCollectionOrphanCheckGenerohasInfoVideo + " in its generohasInfoVideoCollection field has a non-nullable generoidGenero field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(genero);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Genero> findGeneroEntities() {
        return findGeneroEntities(true, -1, -1);
    }

    public List<Genero> findGeneroEntities(int maxResults, int firstResult) {
        return findGeneroEntities(false, maxResults, firstResult);
    }

    private List<Genero> findGeneroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Genero.class));
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

    public Genero findGenero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Genero.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeneroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Genero> rt = cq.from(Genero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
