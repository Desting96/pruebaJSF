/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Genero;
import entidades.GenerohasInfoVideo;
import entidades.InfoVideo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author desting
 */
public class GenerohasInfoVideoJpaController implements Serializable {

    public GenerohasInfoVideoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GenerohasInfoVideo generohasInfoVideo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Genero generoidGenero = generohasInfoVideo.getGeneroidGenero();
            if (generoidGenero != null) {
                generoidGenero = em.getReference(generoidGenero.getClass(), generoidGenero.getIdGenero());
                generohasInfoVideo.setGeneroidGenero(generoidGenero);
            }
            InfoVideo infoVideoidInfoVideo = generohasInfoVideo.getInfoVideoidInfoVideo();
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo = em.getReference(infoVideoidInfoVideo.getClass(), infoVideoidInfoVideo.getIdInfoVideo());
                generohasInfoVideo.setInfoVideoidInfoVideo(infoVideoidInfoVideo);
            }
            em.persist(generohasInfoVideo);
            if (generoidGenero != null) {
                generoidGenero.getGenerohasInfoVideoCollection().add(generohasInfoVideo);
                generoidGenero = em.merge(generoidGenero);
            }
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo.getGenerohasInfoVideoCollection().add(generohasInfoVideo);
                infoVideoidInfoVideo = em.merge(infoVideoidInfoVideo);
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

    public void edit(GenerohasInfoVideo generohasInfoVideo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            GenerohasInfoVideo persistentGenerohasInfoVideo = em.find(GenerohasInfoVideo.class, generohasInfoVideo.getIdGenerohasInfoVideo());
            Genero generoidGeneroOld = persistentGenerohasInfoVideo.getGeneroidGenero();
            Genero generoidGeneroNew = generohasInfoVideo.getGeneroidGenero();
            InfoVideo infoVideoidInfoVideoOld = persistentGenerohasInfoVideo.getInfoVideoidInfoVideo();
            InfoVideo infoVideoidInfoVideoNew = generohasInfoVideo.getInfoVideoidInfoVideo();
            if (generoidGeneroNew != null) {
                generoidGeneroNew = em.getReference(generoidGeneroNew.getClass(), generoidGeneroNew.getIdGenero());
                generohasInfoVideo.setGeneroidGenero(generoidGeneroNew);
            }
            if (infoVideoidInfoVideoNew != null) {
                infoVideoidInfoVideoNew = em.getReference(infoVideoidInfoVideoNew.getClass(), infoVideoidInfoVideoNew.getIdInfoVideo());
                generohasInfoVideo.setInfoVideoidInfoVideo(infoVideoidInfoVideoNew);
            }
            generohasInfoVideo = em.merge(generohasInfoVideo);
            if (generoidGeneroOld != null && !generoidGeneroOld.equals(generoidGeneroNew)) {
                generoidGeneroOld.getGenerohasInfoVideoCollection().remove(generohasInfoVideo);
                generoidGeneroOld = em.merge(generoidGeneroOld);
            }
            if (generoidGeneroNew != null && !generoidGeneroNew.equals(generoidGeneroOld)) {
                generoidGeneroNew.getGenerohasInfoVideoCollection().add(generohasInfoVideo);
                generoidGeneroNew = em.merge(generoidGeneroNew);
            }
            if (infoVideoidInfoVideoOld != null && !infoVideoidInfoVideoOld.equals(infoVideoidInfoVideoNew)) {
                infoVideoidInfoVideoOld.getGenerohasInfoVideoCollection().remove(generohasInfoVideo);
                infoVideoidInfoVideoOld = em.merge(infoVideoidInfoVideoOld);
            }
            if (infoVideoidInfoVideoNew != null && !infoVideoidInfoVideoNew.equals(infoVideoidInfoVideoOld)) {
                infoVideoidInfoVideoNew.getGenerohasInfoVideoCollection().add(generohasInfoVideo);
                infoVideoidInfoVideoNew = em.merge(infoVideoidInfoVideoNew);
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
                Integer id = generohasInfoVideo.getIdGenerohasInfoVideo();
                if (findGenerohasInfoVideo(id) == null) {
                    throw new NonexistentEntityException("The generohasInfoVideo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            GenerohasInfoVideo generohasInfoVideo;
            try {
                generohasInfoVideo = em.getReference(GenerohasInfoVideo.class, id);
                generohasInfoVideo.getIdGenerohasInfoVideo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The generohasInfoVideo with id " + id + " no longer exists.", enfe);
            }
            Genero generoidGenero = generohasInfoVideo.getGeneroidGenero();
            if (generoidGenero != null) {
                generoidGenero.getGenerohasInfoVideoCollection().remove(generohasInfoVideo);
                generoidGenero = em.merge(generoidGenero);
            }
            InfoVideo infoVideoidInfoVideo = generohasInfoVideo.getInfoVideoidInfoVideo();
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo.getGenerohasInfoVideoCollection().remove(generohasInfoVideo);
                infoVideoidInfoVideo = em.merge(infoVideoidInfoVideo);
            }
            em.remove(generohasInfoVideo);
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

    public List<GenerohasInfoVideo> findGenerohasInfoVideoEntities() {
        return findGenerohasInfoVideoEntities(true, -1, -1);
    }

    public List<GenerohasInfoVideo> findGenerohasInfoVideoEntities(int maxResults, int firstResult) {
        return findGenerohasInfoVideoEntities(false, maxResults, firstResult);
    }

    private List<GenerohasInfoVideo> findGenerohasInfoVideoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GenerohasInfoVideo.class));
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

    public GenerohasInfoVideo findGenerohasInfoVideo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GenerohasInfoVideo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGenerohasInfoVideoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GenerohasInfoVideo> rt = cq.from(GenerohasInfoVideo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
