/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.InfoVideo;
import entidades.Video;
import entidades.VideohasSubtitulo;
import java.util.ArrayList;
import java.util.Collection;
import entidades.VideohasLenguaje;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author desting
 */
public class VideoJpaController implements Serializable {

    public VideoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Video video) throws RollbackFailureException, Exception {
        if (video.getVideohasSubtituloCollection() == null) {
            video.setVideohasSubtituloCollection(new ArrayList<VideohasSubtitulo>());
        }
        if (video.getVideohasLenguajeCollection() == null) {
            video.setVideohasLenguajeCollection(new ArrayList<VideohasLenguaje>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            InfoVideo infoVideoidInfoVideo = video.getInfoVideoidInfoVideo();
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo = em.getReference(infoVideoidInfoVideo.getClass(), infoVideoidInfoVideo.getIdInfoVideo());
                video.setInfoVideoidInfoVideo(infoVideoidInfoVideo);
            }
            Collection<VideohasSubtitulo> attachedVideohasSubtituloCollection = new ArrayList<VideohasSubtitulo>();
            for (VideohasSubtitulo videohasSubtituloCollectionVideohasSubtituloToAttach : video.getVideohasSubtituloCollection()) {
                videohasSubtituloCollectionVideohasSubtituloToAttach = em.getReference(videohasSubtituloCollectionVideohasSubtituloToAttach.getClass(), videohasSubtituloCollectionVideohasSubtituloToAttach.getIdVideohasSubtitulo());
                attachedVideohasSubtituloCollection.add(videohasSubtituloCollectionVideohasSubtituloToAttach);
            }
            video.setVideohasSubtituloCollection(attachedVideohasSubtituloCollection);
            Collection<VideohasLenguaje> attachedVideohasLenguajeCollection = new ArrayList<VideohasLenguaje>();
            for (VideohasLenguaje videohasLenguajeCollectionVideohasLenguajeToAttach : video.getVideohasLenguajeCollection()) {
                videohasLenguajeCollectionVideohasLenguajeToAttach = em.getReference(videohasLenguajeCollectionVideohasLenguajeToAttach.getClass(), videohasLenguajeCollectionVideohasLenguajeToAttach.getIdVideohasLenguaje());
                attachedVideohasLenguajeCollection.add(videohasLenguajeCollectionVideohasLenguajeToAttach);
            }
            video.setVideohasLenguajeCollection(attachedVideohasLenguajeCollection);
            em.persist(video);
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo.getVideoCollection().add(video);
                infoVideoidInfoVideo = em.merge(infoVideoidInfoVideo);
            }
            for (VideohasSubtitulo videohasSubtituloCollectionVideohasSubtitulo : video.getVideohasSubtituloCollection()) {
                Video oldVideoidVideoOfVideohasSubtituloCollectionVideohasSubtitulo = videohasSubtituloCollectionVideohasSubtitulo.getVideoidVideo();
                videohasSubtituloCollectionVideohasSubtitulo.setVideoidVideo(video);
                videohasSubtituloCollectionVideohasSubtitulo = em.merge(videohasSubtituloCollectionVideohasSubtitulo);
                if (oldVideoidVideoOfVideohasSubtituloCollectionVideohasSubtitulo != null) {
                    oldVideoidVideoOfVideohasSubtituloCollectionVideohasSubtitulo.getVideohasSubtituloCollection().remove(videohasSubtituloCollectionVideohasSubtitulo);
                    oldVideoidVideoOfVideohasSubtituloCollectionVideohasSubtitulo = em.merge(oldVideoidVideoOfVideohasSubtituloCollectionVideohasSubtitulo);
                }
            }
            for (VideohasLenguaje videohasLenguajeCollectionVideohasLenguaje : video.getVideohasLenguajeCollection()) {
                Video oldVideoidVideoOfVideohasLenguajeCollectionVideohasLenguaje = videohasLenguajeCollectionVideohasLenguaje.getVideoidVideo();
                videohasLenguajeCollectionVideohasLenguaje.setVideoidVideo(video);
                videohasLenguajeCollectionVideohasLenguaje = em.merge(videohasLenguajeCollectionVideohasLenguaje);
                if (oldVideoidVideoOfVideohasLenguajeCollectionVideohasLenguaje != null) {
                    oldVideoidVideoOfVideohasLenguajeCollectionVideohasLenguaje.getVideohasLenguajeCollection().remove(videohasLenguajeCollectionVideohasLenguaje);
                    oldVideoidVideoOfVideohasLenguajeCollectionVideohasLenguaje = em.merge(oldVideoidVideoOfVideohasLenguajeCollectionVideohasLenguaje);
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

    public void edit(Video video) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Video persistentVideo = em.find(Video.class, video.getIdVideo());
            InfoVideo infoVideoidInfoVideoOld = persistentVideo.getInfoVideoidInfoVideo();
            InfoVideo infoVideoidInfoVideoNew = video.getInfoVideoidInfoVideo();
            Collection<VideohasSubtitulo> videohasSubtituloCollectionOld = persistentVideo.getVideohasSubtituloCollection();
            Collection<VideohasSubtitulo> videohasSubtituloCollectionNew = video.getVideohasSubtituloCollection();
            Collection<VideohasLenguaje> videohasLenguajeCollectionOld = persistentVideo.getVideohasLenguajeCollection();
            Collection<VideohasLenguaje> videohasLenguajeCollectionNew = video.getVideohasLenguajeCollection();
            List<String> illegalOrphanMessages = null;
            for (VideohasSubtitulo videohasSubtituloCollectionOldVideohasSubtitulo : videohasSubtituloCollectionOld) {
                if (!videohasSubtituloCollectionNew.contains(videohasSubtituloCollectionOldVideohasSubtitulo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VideohasSubtitulo " + videohasSubtituloCollectionOldVideohasSubtitulo + " since its videoidVideo field is not nullable.");
                }
            }
            for (VideohasLenguaje videohasLenguajeCollectionOldVideohasLenguaje : videohasLenguajeCollectionOld) {
                if (!videohasLenguajeCollectionNew.contains(videohasLenguajeCollectionOldVideohasLenguaje)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VideohasLenguaje " + videohasLenguajeCollectionOldVideohasLenguaje + " since its videoidVideo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (infoVideoidInfoVideoNew != null) {
                infoVideoidInfoVideoNew = em.getReference(infoVideoidInfoVideoNew.getClass(), infoVideoidInfoVideoNew.getIdInfoVideo());
                video.setInfoVideoidInfoVideo(infoVideoidInfoVideoNew);
            }
            Collection<VideohasSubtitulo> attachedVideohasSubtituloCollectionNew = new ArrayList<VideohasSubtitulo>();
            for (VideohasSubtitulo videohasSubtituloCollectionNewVideohasSubtituloToAttach : videohasSubtituloCollectionNew) {
                videohasSubtituloCollectionNewVideohasSubtituloToAttach = em.getReference(videohasSubtituloCollectionNewVideohasSubtituloToAttach.getClass(), videohasSubtituloCollectionNewVideohasSubtituloToAttach.getIdVideohasSubtitulo());
                attachedVideohasSubtituloCollectionNew.add(videohasSubtituloCollectionNewVideohasSubtituloToAttach);
            }
            videohasSubtituloCollectionNew = attachedVideohasSubtituloCollectionNew;
            video.setVideohasSubtituloCollection(videohasSubtituloCollectionNew);
            Collection<VideohasLenguaje> attachedVideohasLenguajeCollectionNew = new ArrayList<VideohasLenguaje>();
            for (VideohasLenguaje videohasLenguajeCollectionNewVideohasLenguajeToAttach : videohasLenguajeCollectionNew) {
                videohasLenguajeCollectionNewVideohasLenguajeToAttach = em.getReference(videohasLenguajeCollectionNewVideohasLenguajeToAttach.getClass(), videohasLenguajeCollectionNewVideohasLenguajeToAttach.getIdVideohasLenguaje());
                attachedVideohasLenguajeCollectionNew.add(videohasLenguajeCollectionNewVideohasLenguajeToAttach);
            }
            videohasLenguajeCollectionNew = attachedVideohasLenguajeCollectionNew;
            video.setVideohasLenguajeCollection(videohasLenguajeCollectionNew);
            video = em.merge(video);
            if (infoVideoidInfoVideoOld != null && !infoVideoidInfoVideoOld.equals(infoVideoidInfoVideoNew)) {
                infoVideoidInfoVideoOld.getVideoCollection().remove(video);
                infoVideoidInfoVideoOld = em.merge(infoVideoidInfoVideoOld);
            }
            if (infoVideoidInfoVideoNew != null && !infoVideoidInfoVideoNew.equals(infoVideoidInfoVideoOld)) {
                infoVideoidInfoVideoNew.getVideoCollection().add(video);
                infoVideoidInfoVideoNew = em.merge(infoVideoidInfoVideoNew);
            }
            for (VideohasSubtitulo videohasSubtituloCollectionNewVideohasSubtitulo : videohasSubtituloCollectionNew) {
                if (!videohasSubtituloCollectionOld.contains(videohasSubtituloCollectionNewVideohasSubtitulo)) {
                    Video oldVideoidVideoOfVideohasSubtituloCollectionNewVideohasSubtitulo = videohasSubtituloCollectionNewVideohasSubtitulo.getVideoidVideo();
                    videohasSubtituloCollectionNewVideohasSubtitulo.setVideoidVideo(video);
                    videohasSubtituloCollectionNewVideohasSubtitulo = em.merge(videohasSubtituloCollectionNewVideohasSubtitulo);
                    if (oldVideoidVideoOfVideohasSubtituloCollectionNewVideohasSubtitulo != null && !oldVideoidVideoOfVideohasSubtituloCollectionNewVideohasSubtitulo.equals(video)) {
                        oldVideoidVideoOfVideohasSubtituloCollectionNewVideohasSubtitulo.getVideohasSubtituloCollection().remove(videohasSubtituloCollectionNewVideohasSubtitulo);
                        oldVideoidVideoOfVideohasSubtituloCollectionNewVideohasSubtitulo = em.merge(oldVideoidVideoOfVideohasSubtituloCollectionNewVideohasSubtitulo);
                    }
                }
            }
            for (VideohasLenguaje videohasLenguajeCollectionNewVideohasLenguaje : videohasLenguajeCollectionNew) {
                if (!videohasLenguajeCollectionOld.contains(videohasLenguajeCollectionNewVideohasLenguaje)) {
                    Video oldVideoidVideoOfVideohasLenguajeCollectionNewVideohasLenguaje = videohasLenguajeCollectionNewVideohasLenguaje.getVideoidVideo();
                    videohasLenguajeCollectionNewVideohasLenguaje.setVideoidVideo(video);
                    videohasLenguajeCollectionNewVideohasLenguaje = em.merge(videohasLenguajeCollectionNewVideohasLenguaje);
                    if (oldVideoidVideoOfVideohasLenguajeCollectionNewVideohasLenguaje != null && !oldVideoidVideoOfVideohasLenguajeCollectionNewVideohasLenguaje.equals(video)) {
                        oldVideoidVideoOfVideohasLenguajeCollectionNewVideohasLenguaje.getVideohasLenguajeCollection().remove(videohasLenguajeCollectionNewVideohasLenguaje);
                        oldVideoidVideoOfVideohasLenguajeCollectionNewVideohasLenguaje = em.merge(oldVideoidVideoOfVideohasLenguajeCollectionNewVideohasLenguaje);
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
                Integer id = video.getIdVideo();
                if (findVideo(id) == null) {
                    throw new NonexistentEntityException("The video with id " + id + " no longer exists.");
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
            Video video;
            try {
                video = em.getReference(Video.class, id);
                video.getIdVideo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The video with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<VideohasSubtitulo> videohasSubtituloCollectionOrphanCheck = video.getVideohasSubtituloCollection();
            for (VideohasSubtitulo videohasSubtituloCollectionOrphanCheckVideohasSubtitulo : videohasSubtituloCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Video (" + video + ") cannot be destroyed since the VideohasSubtitulo " + videohasSubtituloCollectionOrphanCheckVideohasSubtitulo + " in its videohasSubtituloCollection field has a non-nullable videoidVideo field.");
            }
            Collection<VideohasLenguaje> videohasLenguajeCollectionOrphanCheck = video.getVideohasLenguajeCollection();
            for (VideohasLenguaje videohasLenguajeCollectionOrphanCheckVideohasLenguaje : videohasLenguajeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Video (" + video + ") cannot be destroyed since the VideohasLenguaje " + videohasLenguajeCollectionOrphanCheckVideohasLenguaje + " in its videohasLenguajeCollection field has a non-nullable videoidVideo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            InfoVideo infoVideoidInfoVideo = video.getInfoVideoidInfoVideo();
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo.getVideoCollection().remove(video);
                infoVideoidInfoVideo = em.merge(infoVideoidInfoVideo);
            }
            em.remove(video);
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

    public List<Video> findVideoEntities() {
        return findVideoEntities(true, -1, -1);
    }

    public List<Video> findVideoEntities(int maxResults, int firstResult) {
        return findVideoEntities(false, maxResults, firstResult);
    }

    private List<Video> findVideoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Video.class));
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

    public Video findVideo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Video.class, id);
        } finally {
            em.close();
        }
    }

    public int getVideoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Video> rt = cq.from(Video.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
