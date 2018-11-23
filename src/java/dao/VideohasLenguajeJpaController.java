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
import entidades.Lenguaje;
import entidades.Video;
import entidades.VideohasLenguaje;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author desting
 */
public class VideohasLenguajeJpaController implements Serializable {

    public VideohasLenguajeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VideohasLenguaje videohasLenguaje) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Lenguaje lenguajeidLenguaje = videohasLenguaje.getLenguajeidLenguaje();
            if (lenguajeidLenguaje != null) {
                lenguajeidLenguaje = em.getReference(lenguajeidLenguaje.getClass(), lenguajeidLenguaje.getIdLenguaje());
                videohasLenguaje.setLenguajeidLenguaje(lenguajeidLenguaje);
            }
            Video videoidVideo = videohasLenguaje.getVideoidVideo();
            if (videoidVideo != null) {
                videoidVideo = em.getReference(videoidVideo.getClass(), videoidVideo.getIdVideo());
                videohasLenguaje.setVideoidVideo(videoidVideo);
            }
            em.persist(videohasLenguaje);
            if (lenguajeidLenguaje != null) {
                lenguajeidLenguaje.getVideohasLenguajeCollection().add(videohasLenguaje);
                lenguajeidLenguaje = em.merge(lenguajeidLenguaje);
            }
            if (videoidVideo != null) {
                videoidVideo.getVideohasLenguajeCollection().add(videohasLenguaje);
                videoidVideo = em.merge(videoidVideo);
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

    public void edit(VideohasLenguaje videohasLenguaje) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            VideohasLenguaje persistentVideohasLenguaje = em.find(VideohasLenguaje.class, videohasLenguaje.getIdVideohasLenguaje());
            Lenguaje lenguajeidLenguajeOld = persistentVideohasLenguaje.getLenguajeidLenguaje();
            Lenguaje lenguajeidLenguajeNew = videohasLenguaje.getLenguajeidLenguaje();
            Video videoidVideoOld = persistentVideohasLenguaje.getVideoidVideo();
            Video videoidVideoNew = videohasLenguaje.getVideoidVideo();
            if (lenguajeidLenguajeNew != null) {
                lenguajeidLenguajeNew = em.getReference(lenguajeidLenguajeNew.getClass(), lenguajeidLenguajeNew.getIdLenguaje());
                videohasLenguaje.setLenguajeidLenguaje(lenguajeidLenguajeNew);
            }
            if (videoidVideoNew != null) {
                videoidVideoNew = em.getReference(videoidVideoNew.getClass(), videoidVideoNew.getIdVideo());
                videohasLenguaje.setVideoidVideo(videoidVideoNew);
            }
            videohasLenguaje = em.merge(videohasLenguaje);
            if (lenguajeidLenguajeOld != null && !lenguajeidLenguajeOld.equals(lenguajeidLenguajeNew)) {
                lenguajeidLenguajeOld.getVideohasLenguajeCollection().remove(videohasLenguaje);
                lenguajeidLenguajeOld = em.merge(lenguajeidLenguajeOld);
            }
            if (lenguajeidLenguajeNew != null && !lenguajeidLenguajeNew.equals(lenguajeidLenguajeOld)) {
                lenguajeidLenguajeNew.getVideohasLenguajeCollection().add(videohasLenguaje);
                lenguajeidLenguajeNew = em.merge(lenguajeidLenguajeNew);
            }
            if (videoidVideoOld != null && !videoidVideoOld.equals(videoidVideoNew)) {
                videoidVideoOld.getVideohasLenguajeCollection().remove(videohasLenguaje);
                videoidVideoOld = em.merge(videoidVideoOld);
            }
            if (videoidVideoNew != null && !videoidVideoNew.equals(videoidVideoOld)) {
                videoidVideoNew.getVideohasLenguajeCollection().add(videohasLenguaje);
                videoidVideoNew = em.merge(videoidVideoNew);
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
                Integer id = videohasLenguaje.getIdVideohasLenguaje();
                if (findVideohasLenguaje(id) == null) {
                    throw new NonexistentEntityException("The videohasLenguaje with id " + id + " no longer exists.");
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
            VideohasLenguaje videohasLenguaje;
            try {
                videohasLenguaje = em.getReference(VideohasLenguaje.class, id);
                videohasLenguaje.getIdVideohasLenguaje();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The videohasLenguaje with id " + id + " no longer exists.", enfe);
            }
            Lenguaje lenguajeidLenguaje = videohasLenguaje.getLenguajeidLenguaje();
            if (lenguajeidLenguaje != null) {
                lenguajeidLenguaje.getVideohasLenguajeCollection().remove(videohasLenguaje);
                lenguajeidLenguaje = em.merge(lenguajeidLenguaje);
            }
            Video videoidVideo = videohasLenguaje.getVideoidVideo();
            if (videoidVideo != null) {
                videoidVideo.getVideohasLenguajeCollection().remove(videohasLenguaje);
                videoidVideo = em.merge(videoidVideo);
            }
            em.remove(videohasLenguaje);
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

    public List<VideohasLenguaje> findVideohasLenguajeEntities() {
        return findVideohasLenguajeEntities(true, -1, -1);
    }

    public List<VideohasLenguaje> findVideohasLenguajeEntities(int maxResults, int firstResult) {
        return findVideohasLenguajeEntities(false, maxResults, firstResult);
    }

    private List<VideohasLenguaje> findVideohasLenguajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VideohasLenguaje.class));
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

    public VideohasLenguaje findVideohasLenguaje(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VideohasLenguaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getVideohasLenguajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VideohasLenguaje> rt = cq.from(VideohasLenguaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
