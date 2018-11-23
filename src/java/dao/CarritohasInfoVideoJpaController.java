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
import entidades.Carrito;
import entidades.CarritohasInfoVideo;
import entidades.InfoVideo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author desting
 */
public class CarritohasInfoVideoJpaController implements Serializable {

    public CarritohasInfoVideoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CarritohasInfoVideo carritohasInfoVideo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Carrito carritoidCarrito = carritohasInfoVideo.getCarritoidCarrito();
            if (carritoidCarrito != null) {
                carritoidCarrito = em.getReference(carritoidCarrito.getClass(), carritoidCarrito.getIdCarrito());
                carritohasInfoVideo.setCarritoidCarrito(carritoidCarrito);
            }
            InfoVideo infoVideoidInfoVideo = carritohasInfoVideo.getInfoVideoidInfoVideo();
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo = em.getReference(infoVideoidInfoVideo.getClass(), infoVideoidInfoVideo.getIdInfoVideo());
                carritohasInfoVideo.setInfoVideoidInfoVideo(infoVideoidInfoVideo);
            }
            em.persist(carritohasInfoVideo);
            if (carritoidCarrito != null) {
                carritoidCarrito.getCarritohasInfoVideoCollection().add(carritohasInfoVideo);
                carritoidCarrito = em.merge(carritoidCarrito);
            }
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo.getCarritohasInfoVideoCollection().add(carritohasInfoVideo);
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

    public void edit(CarritohasInfoVideo carritohasInfoVideo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CarritohasInfoVideo persistentCarritohasInfoVideo = em.find(CarritohasInfoVideo.class, carritohasInfoVideo.getIdCarritohasInfoVideo());
            Carrito carritoidCarritoOld = persistentCarritohasInfoVideo.getCarritoidCarrito();
            Carrito carritoidCarritoNew = carritohasInfoVideo.getCarritoidCarrito();
            InfoVideo infoVideoidInfoVideoOld = persistentCarritohasInfoVideo.getInfoVideoidInfoVideo();
            InfoVideo infoVideoidInfoVideoNew = carritohasInfoVideo.getInfoVideoidInfoVideo();
            if (carritoidCarritoNew != null) {
                carritoidCarritoNew = em.getReference(carritoidCarritoNew.getClass(), carritoidCarritoNew.getIdCarrito());
                carritohasInfoVideo.setCarritoidCarrito(carritoidCarritoNew);
            }
            if (infoVideoidInfoVideoNew != null) {
                infoVideoidInfoVideoNew = em.getReference(infoVideoidInfoVideoNew.getClass(), infoVideoidInfoVideoNew.getIdInfoVideo());
                carritohasInfoVideo.setInfoVideoidInfoVideo(infoVideoidInfoVideoNew);
            }
            carritohasInfoVideo = em.merge(carritohasInfoVideo);
            if (carritoidCarritoOld != null && !carritoidCarritoOld.equals(carritoidCarritoNew)) {
                carritoidCarritoOld.getCarritohasInfoVideoCollection().remove(carritohasInfoVideo);
                carritoidCarritoOld = em.merge(carritoidCarritoOld);
            }
            if (carritoidCarritoNew != null && !carritoidCarritoNew.equals(carritoidCarritoOld)) {
                carritoidCarritoNew.getCarritohasInfoVideoCollection().add(carritohasInfoVideo);
                carritoidCarritoNew = em.merge(carritoidCarritoNew);
            }
            if (infoVideoidInfoVideoOld != null && !infoVideoidInfoVideoOld.equals(infoVideoidInfoVideoNew)) {
                infoVideoidInfoVideoOld.getCarritohasInfoVideoCollection().remove(carritohasInfoVideo);
                infoVideoidInfoVideoOld = em.merge(infoVideoidInfoVideoOld);
            }
            if (infoVideoidInfoVideoNew != null && !infoVideoidInfoVideoNew.equals(infoVideoidInfoVideoOld)) {
                infoVideoidInfoVideoNew.getCarritohasInfoVideoCollection().add(carritohasInfoVideo);
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
                Integer id = carritohasInfoVideo.getIdCarritohasInfoVideo();
                if (findCarritohasInfoVideo(id) == null) {
                    throw new NonexistentEntityException("The carritohasInfoVideo with id " + id + " no longer exists.");
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
            CarritohasInfoVideo carritohasInfoVideo;
            try {
                carritohasInfoVideo = em.getReference(CarritohasInfoVideo.class, id);
                carritohasInfoVideo.getIdCarritohasInfoVideo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carritohasInfoVideo with id " + id + " no longer exists.", enfe);
            }
            Carrito carritoidCarrito = carritohasInfoVideo.getCarritoidCarrito();
            if (carritoidCarrito != null) {
                carritoidCarrito.getCarritohasInfoVideoCollection().remove(carritohasInfoVideo);
                carritoidCarrito = em.merge(carritoidCarrito);
            }
            InfoVideo infoVideoidInfoVideo = carritohasInfoVideo.getInfoVideoidInfoVideo();
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo.getCarritohasInfoVideoCollection().remove(carritohasInfoVideo);
                infoVideoidInfoVideo = em.merge(infoVideoidInfoVideo);
            }
            em.remove(carritohasInfoVideo);
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

    public List<CarritohasInfoVideo> findCarritohasInfoVideoEntities() {
        return findCarritohasInfoVideoEntities(true, -1, -1);
    }

    public List<CarritohasInfoVideo> findCarritohasInfoVideoEntities(int maxResults, int firstResult) {
        return findCarritohasInfoVideoEntities(false, maxResults, firstResult);
    }

    private List<CarritohasInfoVideo> findCarritohasInfoVideoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CarritohasInfoVideo.class));
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

    public CarritohasInfoVideo findCarritohasInfoVideo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CarritohasInfoVideo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarritohasInfoVideoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CarritohasInfoVideo> rt = cq.from(CarritohasInfoVideo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
