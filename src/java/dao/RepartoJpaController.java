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
import entidades.Persona;
import entidades.Reparto;
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
public class RepartoJpaController implements Serializable {

    public RepartoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reparto reparto) throws RollbackFailureException, Exception {
        if (reparto.getPersonaCollection() == null) {
            reparto.setPersonaCollection(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Persona> attachedPersonaCollection = new ArrayList<Persona>();
            for (Persona personaCollectionPersonaToAttach : reparto.getPersonaCollection()) {
                personaCollectionPersonaToAttach = em.getReference(personaCollectionPersonaToAttach.getClass(), personaCollectionPersonaToAttach.getIdPersona());
                attachedPersonaCollection.add(personaCollectionPersonaToAttach);
            }
            reparto.setPersonaCollection(attachedPersonaCollection);
            em.persist(reparto);
            for (Persona personaCollectionPersona : reparto.getPersonaCollection()) {
                Reparto oldRepartoidRepartoOfPersonaCollectionPersona = personaCollectionPersona.getRepartoidReparto();
                personaCollectionPersona.setRepartoidReparto(reparto);
                personaCollectionPersona = em.merge(personaCollectionPersona);
                if (oldRepartoidRepartoOfPersonaCollectionPersona != null) {
                    oldRepartoidRepartoOfPersonaCollectionPersona.getPersonaCollection().remove(personaCollectionPersona);
                    oldRepartoidRepartoOfPersonaCollectionPersona = em.merge(oldRepartoidRepartoOfPersonaCollectionPersona);
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

    public void edit(Reparto reparto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Reparto persistentReparto = em.find(Reparto.class, reparto.getIdReparto());
            Collection<Persona> personaCollectionOld = persistentReparto.getPersonaCollection();
            Collection<Persona> personaCollectionNew = reparto.getPersonaCollection();
            List<String> illegalOrphanMessages = null;
            for (Persona personaCollectionOldPersona : personaCollectionOld) {
                if (!personaCollectionNew.contains(personaCollectionOldPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Persona " + personaCollectionOldPersona + " since its repartoidReparto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Persona> attachedPersonaCollectionNew = new ArrayList<Persona>();
            for (Persona personaCollectionNewPersonaToAttach : personaCollectionNew) {
                personaCollectionNewPersonaToAttach = em.getReference(personaCollectionNewPersonaToAttach.getClass(), personaCollectionNewPersonaToAttach.getIdPersona());
                attachedPersonaCollectionNew.add(personaCollectionNewPersonaToAttach);
            }
            personaCollectionNew = attachedPersonaCollectionNew;
            reparto.setPersonaCollection(personaCollectionNew);
            reparto = em.merge(reparto);
            for (Persona personaCollectionNewPersona : personaCollectionNew) {
                if (!personaCollectionOld.contains(personaCollectionNewPersona)) {
                    Reparto oldRepartoidRepartoOfPersonaCollectionNewPersona = personaCollectionNewPersona.getRepartoidReparto();
                    personaCollectionNewPersona.setRepartoidReparto(reparto);
                    personaCollectionNewPersona = em.merge(personaCollectionNewPersona);
                    if (oldRepartoidRepartoOfPersonaCollectionNewPersona != null && !oldRepartoidRepartoOfPersonaCollectionNewPersona.equals(reparto)) {
                        oldRepartoidRepartoOfPersonaCollectionNewPersona.getPersonaCollection().remove(personaCollectionNewPersona);
                        oldRepartoidRepartoOfPersonaCollectionNewPersona = em.merge(oldRepartoidRepartoOfPersonaCollectionNewPersona);
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
                Integer id = reparto.getIdReparto();
                if (findReparto(id) == null) {
                    throw new NonexistentEntityException("The reparto with id " + id + " no longer exists.");
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
            Reparto reparto;
            try {
                reparto = em.getReference(Reparto.class, id);
                reparto.getIdReparto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reparto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Persona> personaCollectionOrphanCheck = reparto.getPersonaCollection();
            for (Persona personaCollectionOrphanCheckPersona : personaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Reparto (" + reparto + ") cannot be destroyed since the Persona " + personaCollectionOrphanCheckPersona + " in its personaCollection field has a non-nullable repartoidReparto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(reparto);
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

    public List<Reparto> findRepartoEntities() {
        return findRepartoEntities(true, -1, -1);
    }

    public List<Reparto> findRepartoEntities(int maxResults, int firstResult) {
        return findRepartoEntities(false, maxResults, firstResult);
    }

    private List<Reparto> findRepartoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reparto.class));
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

    public Reparto findReparto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reparto.class, id);
        } finally {
            em.close();
        }
    }

    public int getRepartoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reparto> rt = cq.from(Reparto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
