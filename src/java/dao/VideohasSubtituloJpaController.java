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
import entidades.Subtitulo;
import entidades.Video;
import entidades.VideohasSubtitulo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author desting
 */
public class VideohasSubtituloJpaController implements Serializable {

    public VideohasSubtituloJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VideohasSubtitulo videohasSubtitulo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Subtitulo subtituloidSubtitulo = videohasSubtitulo.getSubtituloidSubtitulo();
            if (subtituloidSubtitulo != null) {
                subtituloidSubtitulo = em.getReference(subtituloidSubtitulo.getClass(), subtituloidSubtitulo.getIdSubtitulo());
                videohasSubtitulo.setSubtituloidSubtitulo(subtituloidSubtitulo);
            }
            Video videoidVideo = videohasSubtitulo.getVideoidVideo();
            if (videoidVideo != null) {
                videoidVideo = em.getReference(videoidVideo.getClass(), videoidVideo.getIdVideo());
                videohasSubtitulo.setVideoidVideo(videoidVideo);
            }
            em.persist(videohasSubtitulo);
            if (subtituloidSubtitulo != null) {
                subtituloidSubtitulo.getVideohasSubtituloCollection().add(videohasSubtitulo);
                subtituloidSubtitulo = em.merge(subtituloidSubtitulo);
            }
            if (videoidVideo != null) {
                videoidVideo.getVideohasSubtituloCollection().add(videohasSubtitulo);
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

    public void edit(VideohasSubtitulo videohasSubtitulo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            VideohasSubtitulo persistentVideohasSubtitulo = em.find(VideohasSubtitulo.class, videohasSubtitulo.getIdVideohasSubtitulo());
            Subtitulo subtituloidSubtituloOld = persistentVideohasSubtitulo.getSubtituloidSubtitulo();
            Subtitulo subtituloidSubtituloNew = videohasSubtitulo.getSubtituloidSubtitulo();
            Video videoidVideoOld = persistentVideohasSubtitulo.getVideoidVideo();
            Video videoidVideoNew = videohasSubtitulo.getVideoidVideo();
            if (subtituloidSubtituloNew != null) {
                subtituloidSubtituloNew = em.getReference(subtituloidSubtituloNew.getClass(), subtituloidSubtituloNew.getIdSubtitulo());
                videohasSubtitulo.setSubtituloidSubtitulo(subtituloidSubtituloNew);
            }
            if (videoidVideoNew != null) {
                videoidVideoNew = em.getReference(videoidVideoNew.getClass(), videoidVideoNew.getIdVideo());
                videohasSubtitulo.setVideoidVideo(videoidVideoNew);
            }
            videohasSubtitulo = em.merge(videohasSubtitulo);
            if (subtituloidSubtituloOld != null && !subtituloidSubtituloOld.equals(subtituloidSubtituloNew)) {
                subtituloidSubtituloOld.getVideohasSubtituloCollection().remove(videohasSubtitulo);
                subtituloidSubtituloOld = em.merge(subtituloidSubtituloOld);
            }
            if (subtituloidSubtituloNew != null && !subtituloidSubtituloNew.equals(subtituloidSubtituloOld)) {
                subtituloidSubtituloNew.getVideohasSubtituloCollection().add(videohasSubtitulo);
                subtituloidSubtituloNew = em.merge(subtituloidSubtituloNew);
            }
            if (videoidVideoOld != null && !videoidVideoOld.equals(videoidVideoNew)) {
                videoidVideoOld.getVideohasSubtituloCollection().remove(videohasSubtitulo);
                videoidVideoOld = em.merge(videoidVideoOld);
            }
            if (videoidVideoNew != null && !videoidVideoNew.equals(videoidVideoOld)) {
                videoidVideoNew.getVideohasSubtituloCollection().add(videohasSubtitulo);
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
                Integer id = videohasSubtitulo.getIdVideohasSubtitulo();
                if (findVideohasSubtitulo(id) == null) {
                    throw new NonexistentEntityException("The videohasSubtitulo with id " + id + " no longer exists.");
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
            VideohasSubtitulo videohasSubtitulo;
            try {
                videohasSubtitulo = em.getReference(VideohasSubtitulo.class, id);
                videohasSubtitulo.getIdVideohasSubtitulo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The videohasSubtitulo with id " + id + " no longer exists.", enfe);
            }
            Subtitulo subtituloidSubtitulo = videohasSubtitulo.getSubtituloidSubtitulo();
            if (subtituloidSubtitulo != null) {
                subtituloidSubtitulo.getVideohasSubtituloCollection().remove(videohasSubtitulo);
                subtituloidSubtitulo = em.merge(subtituloidSubtitulo);
            }
            Video videoidVideo = videohasSubtitulo.getVideoidVideo();
            if (videoidVideo != null) {
                videoidVideo.getVideohasSubtituloCollection().remove(videohasSubtitulo);
                videoidVideo = em.merge(videoidVideo);
            }
            em.remove(videohasSubtitulo);
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

    public List<VideohasSubtitulo> findVideohasSubtituloEntities() {
        return findVideohasSubtituloEntities(true, -1, -1);
    }

    public List<VideohasSubtitulo> findVideohasSubtituloEntities(int maxResults, int firstResult) {
        return findVideohasSubtituloEntities(false, maxResults, firstResult);
    }

    private List<VideohasSubtitulo> findVideohasSubtituloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VideohasSubtitulo.class));
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

    public VideohasSubtitulo findVideohasSubtitulo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VideohasSubtitulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVideohasSubtituloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VideohasSubtitulo> rt = cq.from(VideohasSubtitulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
