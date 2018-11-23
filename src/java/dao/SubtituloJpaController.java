/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidades.Subtitulo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.VideohasSubtitulo;
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
public class SubtituloJpaController implements Serializable {

    public SubtituloJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subtitulo subtitulo) throws RollbackFailureException, Exception {
        if (subtitulo.getVideohasSubtituloCollection() == null) {
            subtitulo.setVideohasSubtituloCollection(new ArrayList<VideohasSubtitulo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<VideohasSubtitulo> attachedVideohasSubtituloCollection = new ArrayList<VideohasSubtitulo>();
            for (VideohasSubtitulo videohasSubtituloCollectionVideohasSubtituloToAttach : subtitulo.getVideohasSubtituloCollection()) {
                videohasSubtituloCollectionVideohasSubtituloToAttach = em.getReference(videohasSubtituloCollectionVideohasSubtituloToAttach.getClass(), videohasSubtituloCollectionVideohasSubtituloToAttach.getIdVideohasSubtitulo());
                attachedVideohasSubtituloCollection.add(videohasSubtituloCollectionVideohasSubtituloToAttach);
            }
            subtitulo.setVideohasSubtituloCollection(attachedVideohasSubtituloCollection);
            em.persist(subtitulo);
            for (VideohasSubtitulo videohasSubtituloCollectionVideohasSubtitulo : subtitulo.getVideohasSubtituloCollection()) {
                Subtitulo oldSubtituloidSubtituloOfVideohasSubtituloCollectionVideohasSubtitulo = videohasSubtituloCollectionVideohasSubtitulo.getSubtituloidSubtitulo();
                videohasSubtituloCollectionVideohasSubtitulo.setSubtituloidSubtitulo(subtitulo);
                videohasSubtituloCollectionVideohasSubtitulo = em.merge(videohasSubtituloCollectionVideohasSubtitulo);
                if (oldSubtituloidSubtituloOfVideohasSubtituloCollectionVideohasSubtitulo != null) {
                    oldSubtituloidSubtituloOfVideohasSubtituloCollectionVideohasSubtitulo.getVideohasSubtituloCollection().remove(videohasSubtituloCollectionVideohasSubtitulo);
                    oldSubtituloidSubtituloOfVideohasSubtituloCollectionVideohasSubtitulo = em.merge(oldSubtituloidSubtituloOfVideohasSubtituloCollectionVideohasSubtitulo);
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

    public void edit(Subtitulo subtitulo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Subtitulo persistentSubtitulo = em.find(Subtitulo.class, subtitulo.getIdSubtitulo());
            Collection<VideohasSubtitulo> videohasSubtituloCollectionOld = persistentSubtitulo.getVideohasSubtituloCollection();
            Collection<VideohasSubtitulo> videohasSubtituloCollectionNew = subtitulo.getVideohasSubtituloCollection();
            List<String> illegalOrphanMessages = null;
            for (VideohasSubtitulo videohasSubtituloCollectionOldVideohasSubtitulo : videohasSubtituloCollectionOld) {
                if (!videohasSubtituloCollectionNew.contains(videohasSubtituloCollectionOldVideohasSubtitulo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VideohasSubtitulo " + videohasSubtituloCollectionOldVideohasSubtitulo + " since its subtituloidSubtitulo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<VideohasSubtitulo> attachedVideohasSubtituloCollectionNew = new ArrayList<VideohasSubtitulo>();
            for (VideohasSubtitulo videohasSubtituloCollectionNewVideohasSubtituloToAttach : videohasSubtituloCollectionNew) {
                videohasSubtituloCollectionNewVideohasSubtituloToAttach = em.getReference(videohasSubtituloCollectionNewVideohasSubtituloToAttach.getClass(), videohasSubtituloCollectionNewVideohasSubtituloToAttach.getIdVideohasSubtitulo());
                attachedVideohasSubtituloCollectionNew.add(videohasSubtituloCollectionNewVideohasSubtituloToAttach);
            }
            videohasSubtituloCollectionNew = attachedVideohasSubtituloCollectionNew;
            subtitulo.setVideohasSubtituloCollection(videohasSubtituloCollectionNew);
            subtitulo = em.merge(subtitulo);
            for (VideohasSubtitulo videohasSubtituloCollectionNewVideohasSubtitulo : videohasSubtituloCollectionNew) {
                if (!videohasSubtituloCollectionOld.contains(videohasSubtituloCollectionNewVideohasSubtitulo)) {
                    Subtitulo oldSubtituloidSubtituloOfVideohasSubtituloCollectionNewVideohasSubtitulo = videohasSubtituloCollectionNewVideohasSubtitulo.getSubtituloidSubtitulo();
                    videohasSubtituloCollectionNewVideohasSubtitulo.setSubtituloidSubtitulo(subtitulo);
                    videohasSubtituloCollectionNewVideohasSubtitulo = em.merge(videohasSubtituloCollectionNewVideohasSubtitulo);
                    if (oldSubtituloidSubtituloOfVideohasSubtituloCollectionNewVideohasSubtitulo != null && !oldSubtituloidSubtituloOfVideohasSubtituloCollectionNewVideohasSubtitulo.equals(subtitulo)) {
                        oldSubtituloidSubtituloOfVideohasSubtituloCollectionNewVideohasSubtitulo.getVideohasSubtituloCollection().remove(videohasSubtituloCollectionNewVideohasSubtitulo);
                        oldSubtituloidSubtituloOfVideohasSubtituloCollectionNewVideohasSubtitulo = em.merge(oldSubtituloidSubtituloOfVideohasSubtituloCollectionNewVideohasSubtitulo);
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
                Integer id = subtitulo.getIdSubtitulo();
                if (findSubtitulo(id) == null) {
                    throw new NonexistentEntityException("The subtitulo with id " + id + " no longer exists.");
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
            Subtitulo subtitulo;
            try {
                subtitulo = em.getReference(Subtitulo.class, id);
                subtitulo.getIdSubtitulo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subtitulo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<VideohasSubtitulo> videohasSubtituloCollectionOrphanCheck = subtitulo.getVideohasSubtituloCollection();
            for (VideohasSubtitulo videohasSubtituloCollectionOrphanCheckVideohasSubtitulo : videohasSubtituloCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subtitulo (" + subtitulo + ") cannot be destroyed since the VideohasSubtitulo " + videohasSubtituloCollectionOrphanCheckVideohasSubtitulo + " in its videohasSubtituloCollection field has a non-nullable subtituloidSubtitulo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(subtitulo);
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

    public List<Subtitulo> findSubtituloEntities() {
        return findSubtituloEntities(true, -1, -1);
    }

    public List<Subtitulo> findSubtituloEntities(int maxResults, int firstResult) {
        return findSubtituloEntities(false, maxResults, firstResult);
    }

    private List<Subtitulo> findSubtituloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subtitulo.class));
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

    public Subtitulo findSubtitulo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subtitulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubtituloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subtitulo> rt = cq.from(Subtitulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
