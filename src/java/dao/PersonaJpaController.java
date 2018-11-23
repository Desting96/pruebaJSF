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
import entidades.InfoVideo;
import entidades.Persona;
import entidades.Reparto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author desting
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            InfoVideo infoVideoidInfoVideo = persona.getInfoVideoidInfoVideo();
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo = em.getReference(infoVideoidInfoVideo.getClass(), infoVideoidInfoVideo.getIdInfoVideo());
                persona.setInfoVideoidInfoVideo(infoVideoidInfoVideo);
            }
            Reparto repartoidReparto = persona.getRepartoidReparto();
            if (repartoidReparto != null) {
                repartoidReparto = em.getReference(repartoidReparto.getClass(), repartoidReparto.getIdReparto());
                persona.setRepartoidReparto(repartoidReparto);
            }
            em.persist(persona);
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo.getPersonaCollection().add(persona);
                infoVideoidInfoVideo = em.merge(infoVideoidInfoVideo);
            }
            if (repartoidReparto != null) {
                repartoidReparto.getPersonaCollection().add(persona);
                repartoidReparto = em.merge(repartoidReparto);
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

    public void edit(Persona persona) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Persona persistentPersona = em.find(Persona.class, persona.getIdPersona());
            InfoVideo infoVideoidInfoVideoOld = persistentPersona.getInfoVideoidInfoVideo();
            InfoVideo infoVideoidInfoVideoNew = persona.getInfoVideoidInfoVideo();
            Reparto repartoidRepartoOld = persistentPersona.getRepartoidReparto();
            Reparto repartoidRepartoNew = persona.getRepartoidReparto();
            if (infoVideoidInfoVideoNew != null) {
                infoVideoidInfoVideoNew = em.getReference(infoVideoidInfoVideoNew.getClass(), infoVideoidInfoVideoNew.getIdInfoVideo());
                persona.setInfoVideoidInfoVideo(infoVideoidInfoVideoNew);
            }
            if (repartoidRepartoNew != null) {
                repartoidRepartoNew = em.getReference(repartoidRepartoNew.getClass(), repartoidRepartoNew.getIdReparto());
                persona.setRepartoidReparto(repartoidRepartoNew);
            }
            persona = em.merge(persona);
            if (infoVideoidInfoVideoOld != null && !infoVideoidInfoVideoOld.equals(infoVideoidInfoVideoNew)) {
                infoVideoidInfoVideoOld.getPersonaCollection().remove(persona);
                infoVideoidInfoVideoOld = em.merge(infoVideoidInfoVideoOld);
            }
            if (infoVideoidInfoVideoNew != null && !infoVideoidInfoVideoNew.equals(infoVideoidInfoVideoOld)) {
                infoVideoidInfoVideoNew.getPersonaCollection().add(persona);
                infoVideoidInfoVideoNew = em.merge(infoVideoidInfoVideoNew);
            }
            if (repartoidRepartoOld != null && !repartoidRepartoOld.equals(repartoidRepartoNew)) {
                repartoidRepartoOld.getPersonaCollection().remove(persona);
                repartoidRepartoOld = em.merge(repartoidRepartoOld);
            }
            if (repartoidRepartoNew != null && !repartoidRepartoNew.equals(repartoidRepartoOld)) {
                repartoidRepartoNew.getPersonaCollection().add(persona);
                repartoidRepartoNew = em.merge(repartoidRepartoNew);
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
                Integer id = persona.getIdPersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            InfoVideo infoVideoidInfoVideo = persona.getInfoVideoidInfoVideo();
            if (infoVideoidInfoVideo != null) {
                infoVideoidInfoVideo.getPersonaCollection().remove(persona);
                infoVideoidInfoVideo = em.merge(infoVideoidInfoVideo);
            }
            Reparto repartoidReparto = persona.getRepartoidReparto();
            if (repartoidReparto != null) {
                repartoidReparto.getPersonaCollection().remove(persona);
                repartoidReparto = em.merge(repartoidReparto);
            }
            em.remove(persona);
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

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
