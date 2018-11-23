/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidades.Carrito;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Usuario;
import entidades.CarritohasInfoVideo;
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
public class CarritoJpaController implements Serializable {

    public CarritoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carrito carrito) throws RollbackFailureException, Exception {
        if (carrito.getCarritohasInfoVideoCollection() == null) {
            carrito.setCarritohasInfoVideoCollection(new ArrayList<CarritohasInfoVideo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuarioidUsuario = carrito.getUsuarioidUsuario();
            if (usuarioidUsuario != null) {
                usuarioidUsuario = em.getReference(usuarioidUsuario.getClass(), usuarioidUsuario.getIdUsuario());
                carrito.setUsuarioidUsuario(usuarioidUsuario);
            }
            Collection<CarritohasInfoVideo> attachedCarritohasInfoVideoCollection = new ArrayList<CarritohasInfoVideo>();
            for (CarritohasInfoVideo carritohasInfoVideoCollectionCarritohasInfoVideoToAttach : carrito.getCarritohasInfoVideoCollection()) {
                carritohasInfoVideoCollectionCarritohasInfoVideoToAttach = em.getReference(carritohasInfoVideoCollectionCarritohasInfoVideoToAttach.getClass(), carritohasInfoVideoCollectionCarritohasInfoVideoToAttach.getIdCarritohasInfoVideo());
                attachedCarritohasInfoVideoCollection.add(carritohasInfoVideoCollectionCarritohasInfoVideoToAttach);
            }
            carrito.setCarritohasInfoVideoCollection(attachedCarritohasInfoVideoCollection);
            em.persist(carrito);
            if (usuarioidUsuario != null) {
                usuarioidUsuario.getCarritoCollection().add(carrito);
                usuarioidUsuario = em.merge(usuarioidUsuario);
            }
            for (CarritohasInfoVideo carritohasInfoVideoCollectionCarritohasInfoVideo : carrito.getCarritohasInfoVideoCollection()) {
                Carrito oldCarritoidCarritoOfCarritohasInfoVideoCollectionCarritohasInfoVideo = carritohasInfoVideoCollectionCarritohasInfoVideo.getCarritoidCarrito();
                carritohasInfoVideoCollectionCarritohasInfoVideo.setCarritoidCarrito(carrito);
                carritohasInfoVideoCollectionCarritohasInfoVideo = em.merge(carritohasInfoVideoCollectionCarritohasInfoVideo);
                if (oldCarritoidCarritoOfCarritohasInfoVideoCollectionCarritohasInfoVideo != null) {
                    oldCarritoidCarritoOfCarritohasInfoVideoCollectionCarritohasInfoVideo.getCarritohasInfoVideoCollection().remove(carritohasInfoVideoCollectionCarritohasInfoVideo);
                    oldCarritoidCarritoOfCarritohasInfoVideoCollectionCarritohasInfoVideo = em.merge(oldCarritoidCarritoOfCarritohasInfoVideoCollectionCarritohasInfoVideo);
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

    public void edit(Carrito carrito) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Carrito persistentCarrito = em.find(Carrito.class, carrito.getIdCarrito());
            Usuario usuarioidUsuarioOld = persistentCarrito.getUsuarioidUsuario();
            Usuario usuarioidUsuarioNew = carrito.getUsuarioidUsuario();
            Collection<CarritohasInfoVideo> carritohasInfoVideoCollectionOld = persistentCarrito.getCarritohasInfoVideoCollection();
            Collection<CarritohasInfoVideo> carritohasInfoVideoCollectionNew = carrito.getCarritohasInfoVideoCollection();
            List<String> illegalOrphanMessages = null;
            for (CarritohasInfoVideo carritohasInfoVideoCollectionOldCarritohasInfoVideo : carritohasInfoVideoCollectionOld) {
                if (!carritohasInfoVideoCollectionNew.contains(carritohasInfoVideoCollectionOldCarritohasInfoVideo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CarritohasInfoVideo " + carritohasInfoVideoCollectionOldCarritohasInfoVideo + " since its carritoidCarrito field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioidUsuarioNew != null) {
                usuarioidUsuarioNew = em.getReference(usuarioidUsuarioNew.getClass(), usuarioidUsuarioNew.getIdUsuario());
                carrito.setUsuarioidUsuario(usuarioidUsuarioNew);
            }
            Collection<CarritohasInfoVideo> attachedCarritohasInfoVideoCollectionNew = new ArrayList<CarritohasInfoVideo>();
            for (CarritohasInfoVideo carritohasInfoVideoCollectionNewCarritohasInfoVideoToAttach : carritohasInfoVideoCollectionNew) {
                carritohasInfoVideoCollectionNewCarritohasInfoVideoToAttach = em.getReference(carritohasInfoVideoCollectionNewCarritohasInfoVideoToAttach.getClass(), carritohasInfoVideoCollectionNewCarritohasInfoVideoToAttach.getIdCarritohasInfoVideo());
                attachedCarritohasInfoVideoCollectionNew.add(carritohasInfoVideoCollectionNewCarritohasInfoVideoToAttach);
            }
            carritohasInfoVideoCollectionNew = attachedCarritohasInfoVideoCollectionNew;
            carrito.setCarritohasInfoVideoCollection(carritohasInfoVideoCollectionNew);
            carrito = em.merge(carrito);
            if (usuarioidUsuarioOld != null && !usuarioidUsuarioOld.equals(usuarioidUsuarioNew)) {
                usuarioidUsuarioOld.getCarritoCollection().remove(carrito);
                usuarioidUsuarioOld = em.merge(usuarioidUsuarioOld);
            }
            if (usuarioidUsuarioNew != null && !usuarioidUsuarioNew.equals(usuarioidUsuarioOld)) {
                usuarioidUsuarioNew.getCarritoCollection().add(carrito);
                usuarioidUsuarioNew = em.merge(usuarioidUsuarioNew);
            }
            for (CarritohasInfoVideo carritohasInfoVideoCollectionNewCarritohasInfoVideo : carritohasInfoVideoCollectionNew) {
                if (!carritohasInfoVideoCollectionOld.contains(carritohasInfoVideoCollectionNewCarritohasInfoVideo)) {
                    Carrito oldCarritoidCarritoOfCarritohasInfoVideoCollectionNewCarritohasInfoVideo = carritohasInfoVideoCollectionNewCarritohasInfoVideo.getCarritoidCarrito();
                    carritohasInfoVideoCollectionNewCarritohasInfoVideo.setCarritoidCarrito(carrito);
                    carritohasInfoVideoCollectionNewCarritohasInfoVideo = em.merge(carritohasInfoVideoCollectionNewCarritohasInfoVideo);
                    if (oldCarritoidCarritoOfCarritohasInfoVideoCollectionNewCarritohasInfoVideo != null && !oldCarritoidCarritoOfCarritohasInfoVideoCollectionNewCarritohasInfoVideo.equals(carrito)) {
                        oldCarritoidCarritoOfCarritohasInfoVideoCollectionNewCarritohasInfoVideo.getCarritohasInfoVideoCollection().remove(carritohasInfoVideoCollectionNewCarritohasInfoVideo);
                        oldCarritoidCarritoOfCarritohasInfoVideoCollectionNewCarritohasInfoVideo = em.merge(oldCarritoidCarritoOfCarritohasInfoVideoCollectionNewCarritohasInfoVideo);
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
                Integer id = carrito.getIdCarrito();
                if (findCarrito(id) == null) {
                    throw new NonexistentEntityException("The carrito with id " + id + " no longer exists.");
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
            Carrito carrito;
            try {
                carrito = em.getReference(Carrito.class, id);
                carrito.getIdCarrito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carrito with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CarritohasInfoVideo> carritohasInfoVideoCollectionOrphanCheck = carrito.getCarritohasInfoVideoCollection();
            for (CarritohasInfoVideo carritohasInfoVideoCollectionOrphanCheckCarritohasInfoVideo : carritohasInfoVideoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Carrito (" + carrito + ") cannot be destroyed since the CarritohasInfoVideo " + carritohasInfoVideoCollectionOrphanCheckCarritohasInfoVideo + " in its carritohasInfoVideoCollection field has a non-nullable carritoidCarrito field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuarioidUsuario = carrito.getUsuarioidUsuario();
            if (usuarioidUsuario != null) {
                usuarioidUsuario.getCarritoCollection().remove(carrito);
                usuarioidUsuario = em.merge(usuarioidUsuario);
            }
            em.remove(carrito);
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

    public List<Carrito> findCarritoEntities() {
        return findCarritoEntities(true, -1, -1);
    }

    public List<Carrito> findCarritoEntities(int maxResults, int firstResult) {
        return findCarritoEntities(false, maxResults, firstResult);
    }

    private List<Carrito> findCarritoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carrito.class));
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

    public Carrito findCarrito(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carrito.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarritoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carrito> rt = cq.from(Carrito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
