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
import entidades.RolUsuario;
import entidades.Solicitud;
import java.util.ArrayList;
import java.util.Collection;
import entidades.Carrito;
import entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author desting
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws RollbackFailureException, Exception {
        if (usuario.getSolicitudCollection() == null) {
            usuario.setSolicitudCollection(new ArrayList<Solicitud>());
        }
        if (usuario.getCarritoCollection() == null) {
            usuario.setCarritoCollection(new ArrayList<Carrito>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RolUsuario rolUsuarioidRolUsuario = usuario.getRolUsuarioidRolUsuario();
            if (rolUsuarioidRolUsuario != null) {
                rolUsuarioidRolUsuario = em.getReference(rolUsuarioidRolUsuario.getClass(), rolUsuarioidRolUsuario.getIdRolUsuario());
                usuario.setRolUsuarioidRolUsuario(rolUsuarioidRolUsuario);
            }
            Collection<Solicitud> attachedSolicitudCollection = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionSolicitudToAttach : usuario.getSolicitudCollection()) {
                solicitudCollectionSolicitudToAttach = em.getReference(solicitudCollectionSolicitudToAttach.getClass(), solicitudCollectionSolicitudToAttach.getIdSolicitud());
                attachedSolicitudCollection.add(solicitudCollectionSolicitudToAttach);
            }
            usuario.setSolicitudCollection(attachedSolicitudCollection);
            Collection<Carrito> attachedCarritoCollection = new ArrayList<Carrito>();
            for (Carrito carritoCollectionCarritoToAttach : usuario.getCarritoCollection()) {
                carritoCollectionCarritoToAttach = em.getReference(carritoCollectionCarritoToAttach.getClass(), carritoCollectionCarritoToAttach.getIdCarrito());
                attachedCarritoCollection.add(carritoCollectionCarritoToAttach);
            }
            usuario.setCarritoCollection(attachedCarritoCollection);
            em.persist(usuario);
            if (rolUsuarioidRolUsuario != null) {
                rolUsuarioidRolUsuario.getUsuarioCollection().add(usuario);
                rolUsuarioidRolUsuario = em.merge(rolUsuarioidRolUsuario);
            }
            for (Solicitud solicitudCollectionSolicitud : usuario.getSolicitudCollection()) {
                Usuario oldUsuarioidUsuarioOfSolicitudCollectionSolicitud = solicitudCollectionSolicitud.getUsuarioidUsuario();
                solicitudCollectionSolicitud.setUsuarioidUsuario(usuario);
                solicitudCollectionSolicitud = em.merge(solicitudCollectionSolicitud);
                if (oldUsuarioidUsuarioOfSolicitudCollectionSolicitud != null) {
                    oldUsuarioidUsuarioOfSolicitudCollectionSolicitud.getSolicitudCollection().remove(solicitudCollectionSolicitud);
                    oldUsuarioidUsuarioOfSolicitudCollectionSolicitud = em.merge(oldUsuarioidUsuarioOfSolicitudCollectionSolicitud);
                }
            }
            for (Carrito carritoCollectionCarrito : usuario.getCarritoCollection()) {
                Usuario oldUsuarioidUsuarioOfCarritoCollectionCarrito = carritoCollectionCarrito.getUsuarioidUsuario();
                carritoCollectionCarrito.setUsuarioidUsuario(usuario);
                carritoCollectionCarrito = em.merge(carritoCollectionCarrito);
                if (oldUsuarioidUsuarioOfCarritoCollectionCarrito != null) {
                    oldUsuarioidUsuarioOfCarritoCollectionCarrito.getCarritoCollection().remove(carritoCollectionCarrito);
                    oldUsuarioidUsuarioOfCarritoCollectionCarrito = em.merge(oldUsuarioidUsuarioOfCarritoCollectionCarrito);
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

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            RolUsuario rolUsuarioidRolUsuarioOld = persistentUsuario.getRolUsuarioidRolUsuario();
            RolUsuario rolUsuarioidRolUsuarioNew = usuario.getRolUsuarioidRolUsuario();
            Collection<Solicitud> solicitudCollectionOld = persistentUsuario.getSolicitudCollection();
            Collection<Solicitud> solicitudCollectionNew = usuario.getSolicitudCollection();
            Collection<Carrito> carritoCollectionOld = persistentUsuario.getCarritoCollection();
            Collection<Carrito> carritoCollectionNew = usuario.getCarritoCollection();
            List<String> illegalOrphanMessages = null;
            for (Solicitud solicitudCollectionOldSolicitud : solicitudCollectionOld) {
                if (!solicitudCollectionNew.contains(solicitudCollectionOldSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitud " + solicitudCollectionOldSolicitud + " since its usuarioidUsuario field is not nullable.");
                }
            }
            for (Carrito carritoCollectionOldCarrito : carritoCollectionOld) {
                if (!carritoCollectionNew.contains(carritoCollectionOldCarrito)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Carrito " + carritoCollectionOldCarrito + " since its usuarioidUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (rolUsuarioidRolUsuarioNew != null) {
                rolUsuarioidRolUsuarioNew = em.getReference(rolUsuarioidRolUsuarioNew.getClass(), rolUsuarioidRolUsuarioNew.getIdRolUsuario());
                usuario.setRolUsuarioidRolUsuario(rolUsuarioidRolUsuarioNew);
            }
            Collection<Solicitud> attachedSolicitudCollectionNew = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionNewSolicitudToAttach : solicitudCollectionNew) {
                solicitudCollectionNewSolicitudToAttach = em.getReference(solicitudCollectionNewSolicitudToAttach.getClass(), solicitudCollectionNewSolicitudToAttach.getIdSolicitud());
                attachedSolicitudCollectionNew.add(solicitudCollectionNewSolicitudToAttach);
            }
            solicitudCollectionNew = attachedSolicitudCollectionNew;
            usuario.setSolicitudCollection(solicitudCollectionNew);
            Collection<Carrito> attachedCarritoCollectionNew = new ArrayList<Carrito>();
            for (Carrito carritoCollectionNewCarritoToAttach : carritoCollectionNew) {
                carritoCollectionNewCarritoToAttach = em.getReference(carritoCollectionNewCarritoToAttach.getClass(), carritoCollectionNewCarritoToAttach.getIdCarrito());
                attachedCarritoCollectionNew.add(carritoCollectionNewCarritoToAttach);
            }
            carritoCollectionNew = attachedCarritoCollectionNew;
            usuario.setCarritoCollection(carritoCollectionNew);
            usuario = em.merge(usuario);
            if (rolUsuarioidRolUsuarioOld != null && !rolUsuarioidRolUsuarioOld.equals(rolUsuarioidRolUsuarioNew)) {
                rolUsuarioidRolUsuarioOld.getUsuarioCollection().remove(usuario);
                rolUsuarioidRolUsuarioOld = em.merge(rolUsuarioidRolUsuarioOld);
            }
            if (rolUsuarioidRolUsuarioNew != null && !rolUsuarioidRolUsuarioNew.equals(rolUsuarioidRolUsuarioOld)) {
                rolUsuarioidRolUsuarioNew.getUsuarioCollection().add(usuario);
                rolUsuarioidRolUsuarioNew = em.merge(rolUsuarioidRolUsuarioNew);
            }
            for (Solicitud solicitudCollectionNewSolicitud : solicitudCollectionNew) {
                if (!solicitudCollectionOld.contains(solicitudCollectionNewSolicitud)) {
                    Usuario oldUsuarioidUsuarioOfSolicitudCollectionNewSolicitud = solicitudCollectionNewSolicitud.getUsuarioidUsuario();
                    solicitudCollectionNewSolicitud.setUsuarioidUsuario(usuario);
                    solicitudCollectionNewSolicitud = em.merge(solicitudCollectionNewSolicitud);
                    if (oldUsuarioidUsuarioOfSolicitudCollectionNewSolicitud != null && !oldUsuarioidUsuarioOfSolicitudCollectionNewSolicitud.equals(usuario)) {
                        oldUsuarioidUsuarioOfSolicitudCollectionNewSolicitud.getSolicitudCollection().remove(solicitudCollectionNewSolicitud);
                        oldUsuarioidUsuarioOfSolicitudCollectionNewSolicitud = em.merge(oldUsuarioidUsuarioOfSolicitudCollectionNewSolicitud);
                    }
                }
            }
            for (Carrito carritoCollectionNewCarrito : carritoCollectionNew) {
                if (!carritoCollectionOld.contains(carritoCollectionNewCarrito)) {
                    Usuario oldUsuarioidUsuarioOfCarritoCollectionNewCarrito = carritoCollectionNewCarrito.getUsuarioidUsuario();
                    carritoCollectionNewCarrito.setUsuarioidUsuario(usuario);
                    carritoCollectionNewCarrito = em.merge(carritoCollectionNewCarrito);
                    if (oldUsuarioidUsuarioOfCarritoCollectionNewCarrito != null && !oldUsuarioidUsuarioOfCarritoCollectionNewCarrito.equals(usuario)) {
                        oldUsuarioidUsuarioOfCarritoCollectionNewCarrito.getCarritoCollection().remove(carritoCollectionNewCarrito);
                        oldUsuarioidUsuarioOfCarritoCollectionNewCarrito = em.merge(oldUsuarioidUsuarioOfCarritoCollectionNewCarrito);
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
                Integer id = usuario.getIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Solicitud> solicitudCollectionOrphanCheck = usuario.getSolicitudCollection();
            for (Solicitud solicitudCollectionOrphanCheckSolicitud : solicitudCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Solicitud " + solicitudCollectionOrphanCheckSolicitud + " in its solicitudCollection field has a non-nullable usuarioidUsuario field.");
            }
            Collection<Carrito> carritoCollectionOrphanCheck = usuario.getCarritoCollection();
            for (Carrito carritoCollectionOrphanCheckCarrito : carritoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Carrito " + carritoCollectionOrphanCheckCarrito + " in its carritoCollection field has a non-nullable usuarioidUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            RolUsuario rolUsuarioidRolUsuario = usuario.getRolUsuarioidRolUsuario();
            if (rolUsuarioidRolUsuario != null) {
                rolUsuarioidRolUsuario.getUsuarioCollection().remove(usuario);
                rolUsuarioidRolUsuario = em.merge(rolUsuarioidRolUsuario);
            }
            em.remove(usuario);
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

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
