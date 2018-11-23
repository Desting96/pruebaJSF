/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidades.Calificacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.InfoVideo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author desting
 */
public class CalificacionJpaController implements Serializable {

    public CalificacionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Calificacion calificacion) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            InfoVideo infoVideoidInfoVideo = calificacion.getInfoVideoidInfoVideo();
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo = em.getReference(infoVideoidInfoVideo.getClass(), infoVideoidInfoVideo.getIdInfoVideo());
                calificacion.setInfoVideoidInfoVideo(infoVideoidInfoVideo);
            }
            em.persist(calificacion);
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo.getCalificacionCollection().add(calificacion);
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

    public void edit(Calificacion calificacion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Calificacion persistentCalificacion = em.find(Calificacion.class, calificacion.getIdCalificacion());
            InfoVideo infoVideoidInfoVideoOld = persistentCalificacion.getInfoVideoidInfoVideo();
            InfoVideo infoVideoidInfoVideoNew = calificacion.getInfoVideoidInfoVideo();
            if (infoVideoidInfoVideoNew != null) {
                infoVideoidInfoVideoNew = em.getReference(infoVideoidInfoVideoNew.getClass(), infoVideoidInfoVideoNew.getIdInfoVideo());
                calificacion.setInfoVideoidInfoVideo(infoVideoidInfoVideoNew);
            }
            calificacion = em.merge(calificacion);
            if (infoVideoidInfoVideoOld != null && !infoVideoidInfoVideoOld.equals(infoVideoidInfoVideoNew)) {
                infoVideoidInfoVideoOld.getCalificacionCollection().remove(calificacion);
                infoVideoidInfoVideoOld = em.merge(infoVideoidInfoVideoOld);
            }
            if (infoVideoidInfoVideoNew != null && !infoVideoidInfoVideoNew.equals(infoVideoidInfoVideoOld)) {
                infoVideoidInfoVideoNew.getCalificacionCollection().add(calificacion);
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
                Integer id = calificacion.getIdCalificacion();
                if (findCalificacion(id) == null) {
                    throw new NonexistentEntityException("The calificacion with id " + id + " no longer exists.");
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
            Calificacion calificacion;
            try {
                calificacion = em.getReference(Calificacion.class, id);
                calificacion.getIdCalificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The calificacion with id " + id + " no longer exists.", enfe);
            }
            InfoVideo infoVideoidInfoVideo = calificacion.getInfoVideoidInfoVideo();
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo.getCalificacionCollection().remove(calificacion);
                infoVideoidInfoVideo = em.merge(infoVideoidInfoVideo);
            }
            em.remove(calificacion);
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

    public List<Calificacion> findCalificacionEntities() {
        return findCalificacionEntities(true, -1, -1);
    }

    public List<Calificacion> findCalificacionEntities(int maxResults, int firstResult) {
        return findCalificacionEntities(false, maxResults, firstResult);
    }

    private List<Calificacion> findCalificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Calificacion.class));
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

    public Calificacion findCalificacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Calificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCalificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Calificacion> rt = cq.from(Calificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
