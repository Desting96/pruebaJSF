/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidades.Clasificacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.InfoVideo;
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
public class ClasificacionJpaController implements Serializable {

    public ClasificacionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clasificacion clasificacion) throws RollbackFailureException, Exception {
        if (clasificacion.getInfoVideoCollection() == null) {
            clasificacion.setInfoVideoCollection(new ArrayList<InfoVideo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<InfoVideo> attachedInfoVideoCollection = new ArrayList<InfoVideo>();
            for (InfoVideo infoVideoCollectionInfoVideoToAttach : clasificacion.getInfoVideoCollection()) {
                infoVideoCollectionInfoVideoToAttach = em.getReference(infoVideoCollectionInfoVideoToAttach.getClass(), infoVideoCollectionInfoVideoToAttach.getIdInfoVideo());
                attachedInfoVideoCollection.add(infoVideoCollectionInfoVideoToAttach);
            }
            clasificacion.setInfoVideoCollection(attachedInfoVideoCollection);
            em.persist(clasificacion);
            for (InfoVideo infoVideoCollectionInfoVideo : clasificacion.getInfoVideoCollection()) {
                Clasificacion oldClasificacionidClasificacionOfInfoVideoCollectionInfoVideo = infoVideoCollectionInfoVideo.getClasificacionidClasificacion();
                infoVideoCollectionInfoVideo.setClasificacionidClasificacion(clasificacion);
                infoVideoCollectionInfoVideo = em.merge(infoVideoCollectionInfoVideo);
                if (oldClasificacionidClasificacionOfInfoVideoCollectionInfoVideo != null) {
                    oldClasificacionidClasificacionOfInfoVideoCollectionInfoVideo.getInfoVideoCollection().remove(infoVideoCollectionInfoVideo);
                    oldClasificacionidClasificacionOfInfoVideoCollectionInfoVideo = em.merge(oldClasificacionidClasificacionOfInfoVideoCollectionInfoVideo);
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

    public void edit(Clasificacion clasificacion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Clasificacion persistentClasificacion = em.find(Clasificacion.class, clasificacion.getIdClasificacion());
            Collection<InfoVideo> infoVideoCollectionOld = persistentClasificacion.getInfoVideoCollection();
            Collection<InfoVideo> infoVideoCollectionNew = clasificacion.getInfoVideoCollection();
            List<String> illegalOrphanMessages = null;
            for (InfoVideo infoVideoCollectionOldInfoVideo : infoVideoCollectionOld) {
                if (!infoVideoCollectionNew.contains(infoVideoCollectionOldInfoVideo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InfoVideo " + infoVideoCollectionOldInfoVideo + " since its clasificacionidClasificacion field is not nullable.");
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
            clasificacion.setInfoVideoCollection(infoVideoCollectionNew);
            clasificacion = em.merge(clasificacion);
            for (InfoVideo infoVideoCollectionNewInfoVideo : infoVideoCollectionNew) {
                if (!infoVideoCollectionOld.contains(infoVideoCollectionNewInfoVideo)) {
                    Clasificacion oldClasificacionidClasificacionOfInfoVideoCollectionNewInfoVideo = infoVideoCollectionNewInfoVideo.getClasificacionidClasificacion();
                    infoVideoCollectionNewInfoVideo.setClasificacionidClasificacion(clasificacion);
                    infoVideoCollectionNewInfoVideo = em.merge(infoVideoCollectionNewInfoVideo);
                    if (oldClasificacionidClasificacionOfInfoVideoCollectionNewInfoVideo != null && !oldClasificacionidClasificacionOfInfoVideoCollectionNewInfoVideo.equals(clasificacion)) {
                        oldClasificacionidClasificacionOfInfoVideoCollectionNewInfoVideo.getInfoVideoCollection().remove(infoVideoCollectionNewInfoVideo);
                        oldClasificacionidClasificacionOfInfoVideoCollectionNewInfoVideo = em.merge(oldClasificacionidClasificacionOfInfoVideoCollectionNewInfoVideo);
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
                Integer id = clasificacion.getIdClasificacion();
                if (findClasificacion(id) == null) {
                    throw new NonexistentEntityException("The clasificacion with id " + id + " no longer exists.");
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
            Clasificacion clasificacion;
            try {
                clasificacion = em.getReference(Clasificacion.class, id);
                clasificacion.getIdClasificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clasificacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<InfoVideo> infoVideoCollectionOrphanCheck = clasificacion.getInfoVideoCollection();
            for (InfoVideo infoVideoCollectionOrphanCheckInfoVideo : infoVideoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clasificacion (" + clasificacion + ") cannot be destroyed since the InfoVideo " + infoVideoCollectionOrphanCheckInfoVideo + " in its infoVideoCollection field has a non-nullable clasificacionidClasificacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(clasificacion);
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

    public List<Clasificacion> findClasificacionEntities() {
        return findClasificacionEntities(true, -1, -1);
    }

    public List<Clasificacion> findClasificacionEntities(int maxResults, int firstResult) {
        return findClasificacionEntities(false, maxResults, firstResult);
    }

    private List<Clasificacion> findClasificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clasificacion.class));
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

    public Clasificacion findClasificacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clasificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getClasificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clasificacion> rt = cq.from(Clasificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
