/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidades.Lenguaje;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.VideohasLenguaje;
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
public class LenguajeJpaController implements Serializable {

    public LenguajeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lenguaje lenguaje) throws RollbackFailureException, Exception {
        if (lenguaje.getVideohasLenguajeCollection() == null) {
            lenguaje.setVideohasLenguajeCollection(new ArrayList<VideohasLenguaje>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<VideohasLenguaje> attachedVideohasLenguajeCollection = new ArrayList<VideohasLenguaje>();
            for (VideohasLenguaje videohasLenguajeCollectionVideohasLenguajeToAttach : lenguaje.getVideohasLenguajeCollection()) {
                videohasLenguajeCollectionVideohasLenguajeToAttach = em.getReference(videohasLenguajeCollectionVideohasLenguajeToAttach.getClass(), videohasLenguajeCollectionVideohasLenguajeToAttach.getIdVideohasLenguaje());
                attachedVideohasLenguajeCollection.add(videohasLenguajeCollectionVideohasLenguajeToAttach);
            }
            lenguaje.setVideohasLenguajeCollection(attachedVideohasLenguajeCollection);
            em.persist(lenguaje);
            for (VideohasLenguaje videohasLenguajeCollectionVideohasLenguaje : lenguaje.getVideohasLenguajeCollection()) {
                Lenguaje oldLenguajeidLenguajeOfVideohasLenguajeCollectionVideohasLenguaje = videohasLenguajeCollectionVideohasLenguaje.getLenguajeidLenguaje();
                videohasLenguajeCollectionVideohasLenguaje.setLenguajeidLenguaje(lenguaje);
                videohasLenguajeCollectionVideohasLenguaje = em.merge(videohasLenguajeCollectionVideohasLenguaje);
                if (oldLenguajeidLenguajeOfVideohasLenguajeCollectionVideohasLenguaje != null) {
                    oldLenguajeidLenguajeOfVideohasLenguajeCollectionVideohasLenguaje.getVideohasLenguajeCollection().remove(videohasLenguajeCollectionVideohasLenguaje);
                    oldLenguajeidLenguajeOfVideohasLenguajeCollectionVideohasLenguaje = em.merge(oldLenguajeidLenguajeOfVideohasLenguajeCollectionVideohasLenguaje);
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

    public void edit(Lenguaje lenguaje) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Lenguaje persistentLenguaje = em.find(Lenguaje.class, lenguaje.getIdLenguaje());
            Collection<VideohasLenguaje> videohasLenguajeCollectionOld = persistentLenguaje.getVideohasLenguajeCollection();
            Collection<VideohasLenguaje> videohasLenguajeCollectionNew = lenguaje.getVideohasLenguajeCollection();
            List<String> illegalOrphanMessages = null;
            for (VideohasLenguaje videohasLenguajeCollectionOldVideohasLenguaje : videohasLenguajeCollectionOld) {
                if (!videohasLenguajeCollectionNew.contains(videohasLenguajeCollectionOldVideohasLenguaje)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VideohasLenguaje " + videohasLenguajeCollectionOldVideohasLenguaje + " since its lenguajeidLenguaje field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<VideohasLenguaje> attachedVideohasLenguajeCollectionNew = new ArrayList<VideohasLenguaje>();
            for (VideohasLenguaje videohasLenguajeCollectionNewVideohasLenguajeToAttach : videohasLenguajeCollectionNew) {
                videohasLenguajeCollectionNewVideohasLenguajeToAttach = em.getReference(videohasLenguajeCollectionNewVideohasLenguajeToAttach.getClass(), videohasLenguajeCollectionNewVideohasLenguajeToAttach.getIdVideohasLenguaje());
                attachedVideohasLenguajeCollectionNew.add(videohasLenguajeCollectionNewVideohasLenguajeToAttach);
            }
            videohasLenguajeCollectionNew = attachedVideohasLenguajeCollectionNew;
            lenguaje.setVideohasLenguajeCollection(videohasLenguajeCollectionNew);
            lenguaje = em.merge(lenguaje);
            for (VideohasLenguaje videohasLenguajeCollectionNewVideohasLenguaje : videohasLenguajeCollectionNew) {
                if (!videohasLenguajeCollectionOld.contains(videohasLenguajeCollectionNewVideohasLenguaje)) {
                    Lenguaje oldLenguajeidLenguajeOfVideohasLenguajeCollectionNewVideohasLenguaje = videohasLenguajeCollectionNewVideohasLenguaje.getLenguajeidLenguaje();
                    videohasLenguajeCollectionNewVideohasLenguaje.setLenguajeidLenguaje(lenguaje);
                    videohasLenguajeCollectionNewVideohasLenguaje = em.merge(videohasLenguajeCollectionNewVideohasLenguaje);
                    if (oldLenguajeidLenguajeOfVideohasLenguajeCollectionNewVideohasLenguaje != null && !oldLenguajeidLenguajeOfVideohasLenguajeCollectionNewVideohasLenguaje.equals(lenguaje)) {
                        oldLenguajeidLenguajeOfVideohasLenguajeCollectionNewVideohasLenguaje.getVideohasLenguajeCollection().remove(videohasLenguajeCollectionNewVideohasLenguaje);
                        oldLenguajeidLenguajeOfVideohasLenguajeCollectionNewVideohasLenguaje = em.merge(oldLenguajeidLenguajeOfVideohasLenguajeCollectionNewVideohasLenguaje);
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
                Integer id = lenguaje.getIdLenguaje();
                if (findLenguaje(id) == null) {
                    throw new NonexistentEntityException("The lenguaje with id " + id + " no longer exists.");
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
            Lenguaje lenguaje;
            try {
                lenguaje = em.getReference(Lenguaje.class, id);
                lenguaje.getIdLenguaje();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lenguaje with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<VideohasLenguaje> videohasLenguajeCollectionOrphanCheck = lenguaje.getVideohasLenguajeCollection();
            for (VideohasLenguaje videohasLenguajeCollectionOrphanCheckVideohasLenguaje : videohasLenguajeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Lenguaje (" + lenguaje + ") cannot be destroyed since the VideohasLenguaje " + videohasLenguajeCollectionOrphanCheckVideohasLenguaje + " in its videohasLenguajeCollection field has a non-nullable lenguajeidLenguaje field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(lenguaje);
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

    public List<Lenguaje> findLenguajeEntities() {
        return findLenguajeEntities(true, -1, -1);
    }

    public List<Lenguaje> findLenguajeEntities(int maxResults, int firstResult) {
        return findLenguajeEntities(false, maxResults, firstResult);
    }

    private List<Lenguaje> findLenguajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lenguaje.class));
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

    public Lenguaje findLenguaje(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lenguaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getLenguajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lenguaje> rt = cq.from(Lenguaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
