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
import entidades.TipoVideo;
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
public class TipoVideoJpaController implements Serializable {

    public TipoVideoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoVideo tipoVideo) throws RollbackFailureException, Exception {
        if (tipoVideo.getInfoVideoCollection() == null) {
            tipoVideo.setInfoVideoCollection(new ArrayList<InfoVideo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<InfoVideo> attachedInfoVideoCollection = new ArrayList<InfoVideo>();
            for (InfoVideo infoVideoCollectionInfoVideoToAttach : tipoVideo.getInfoVideoCollection()) {
                infoVideoCollectionInfoVideoToAttach = em.getReference(infoVideoCollectionInfoVideoToAttach.getClass(), infoVideoCollectionInfoVideoToAttach.getIdInfoVideo());
                attachedInfoVideoCollection.add(infoVideoCollectionInfoVideoToAttach);
            }
            tipoVideo.setInfoVideoCollection(attachedInfoVideoCollection);
            em.persist(tipoVideo);
            for (InfoVideo infoVideoCollectionInfoVideo : tipoVideo.getInfoVideoCollection()) {
                TipoVideo oldTipoVideoidTipoVideoOfInfoVideoCollectionInfoVideo = infoVideoCollectionInfoVideo.getTipoVideoidTipoVideo();
                infoVideoCollectionInfoVideo.setTipoVideoidTipoVideo(tipoVideo);
                infoVideoCollectionInfoVideo = em.merge(infoVideoCollectionInfoVideo);
                if (oldTipoVideoidTipoVideoOfInfoVideoCollectionInfoVideo != null) {
                    oldTipoVideoidTipoVideoOfInfoVideoCollectionInfoVideo.getInfoVideoCollection().remove(infoVideoCollectionInfoVideo);
                    oldTipoVideoidTipoVideoOfInfoVideoCollectionInfoVideo = em.merge(oldTipoVideoidTipoVideoOfInfoVideoCollectionInfoVideo);
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

    public void edit(TipoVideo tipoVideo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoVideo persistentTipoVideo = em.find(TipoVideo.class, tipoVideo.getIdTipoVideo());
            Collection<InfoVideo> infoVideoCollectionOld = persistentTipoVideo.getInfoVideoCollection();
            Collection<InfoVideo> infoVideoCollectionNew = tipoVideo.getInfoVideoCollection();
            List<String> illegalOrphanMessages = null;
            for (InfoVideo infoVideoCollectionOldInfoVideo : infoVideoCollectionOld) {
                if (!infoVideoCollectionNew.contains(infoVideoCollectionOldInfoVideo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InfoVideo " + infoVideoCollectionOldInfoVideo + " since its tipoVideoidTipoVideo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<InfoVideo> attachedInfoVideoCollectionNew = new ArrayList<InfoVideo>();
            for (InfoVideo infoVideoCollectionNewInfoVideoToAttach : infoVideoCollectionNew) {
                infoVideoCollectionNewInfoVideoToAttach = em.getReference(infoVideoCollectionNewInfoVideoToAttach.getClass(), infoVideoCollectionNewInfoVideoToAttach.getIdInfoVideo());
                attachedInfoVideoCollectionNew.add(infoVideoCollectionNewInfoVideoToAttach);
            }
            infoVideoCollectionNew = attachedInfoVideoCollectionNew;
            tipoVideo.setInfoVideoCollection(infoVideoCollectionNew);
            tipoVideo = em.merge(tipoVideo);
            for (InfoVideo infoVideoCollectionNewInfoVideo : infoVideoCollectionNew) {
                if (!infoVideoCollectionOld.contains(infoVideoCollectionNewInfoVideo)) {
                    TipoVideo oldTipoVideoidTipoVideoOfInfoVideoCollectionNewInfoVideo = infoVideoCollectionNewInfoVideo.getTipoVideoidTipoVideo();
                    infoVideoCollectionNewInfoVideo.setTipoVideoidTipoVideo(tipoVideo);
                    infoVideoCollectionNewInfoVideo = em.merge(infoVideoCollectionNewInfoVideo);
                    if (oldTipoVideoidTipoVideoOfInfoVideoCollectionNewInfoVideo != null && !oldTipoVideoidTipoVideoOfInfoVideoCollectionNewInfoVideo.equals(tipoVideo)) {
                        oldTipoVideoidTipoVideoOfInfoVideoCollectionNewInfoVideo.getInfoVideoCollection().remove(infoVideoCollectionNewInfoVideo);
                        oldTipoVideoidTipoVideoOfInfoVideoCollectionNewInfoVideo = em.merge(oldTipoVideoidTipoVideoOfInfoVideoCollectionNewInfoVideo);
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
                Integer id = tipoVideo.getIdTipoVideo();
                if (findTipoVideo(id) == null) {
                    throw new NonexistentEntityException("The tipoVideo with id " + id + " no longer exists.");
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
            TipoVideo tipoVideo;
            try {
                tipoVideo = em.getReference(TipoVideo.class, id);
                tipoVideo.getIdTipoVideo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoVideo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<InfoVideo> infoVideoCollectionOrphanCheck = tipoVideo.getInfoVideoCollection();
            for (InfoVideo infoVideoCollectionOrphanCheckInfoVideo : infoVideoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoVideo (" + tipoVideo + ") cannot be destroyed since the InfoVideo " + infoVideoCollectionOrphanCheckInfoVideo + " in its infoVideoCollection field has a non-nullable tipoVideoidTipoVideo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoVideo);
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

    public List<TipoVideo> findTipoVideoEntities() {
        return findTipoVideoEntities(true, -1, -1);
    }

    public List<TipoVideo> findTipoVideoEntities(int maxResults, int firstResult) {
        return findTipoVideoEntities(false, maxResults, firstResult);
    }

    private List<TipoVideo> findTipoVideoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoVideo.class));
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

    public TipoVideo findTipoVideo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoVideo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoVideoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoVideo> rt = cq.from(TipoVideo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
