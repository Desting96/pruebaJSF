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
import entidades.TipoVideo;
import entidades.Clasificacion;
import entidades.Persona;
import java.util.ArrayList;
import java.util.Collection;
import entidades.GenerohasInfoVideo;
import entidades.CarritohasInfoVideo;
import entidades.Calificacion;
import entidades.InfoVideo;
import entidades.Video;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author desting
 */
public class InfoVideoJpaController implements Serializable {

    public InfoVideoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InfoVideo infoVideo) throws RollbackFailureException, Exception {
        if (infoVideo.getPersonaCollection() == null) {
            infoVideo.setPersonaCollection(new ArrayList<Persona>());
        }
        if (infoVideo.getGenerohasInfoVideoCollection() == null) {
            infoVideo.setGenerohasInfoVideoCollection(new ArrayList<GenerohasInfoVideo>());
        }
        if (infoVideo.getCarritohasInfoVideoCollection() == null) {
            infoVideo.setCarritohasInfoVideoCollection(new ArrayList<CarritohasInfoVideo>());
        }
        if (infoVideo.getCalificacionCollection() == null) {
            infoVideo.setCalificacionCollection(new ArrayList<Calificacion>());
        }
        if (infoVideo.getVideoCollection() == null) {
            infoVideo.setVideoCollection(new ArrayList<Video>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoVideo tipoVideoidTipoVideo = infoVideo.getTipoVideoidTipoVideo();
            if (tipoVideoidTipoVideo != null) {
                tipoVideoidTipoVideo = em.getReference(tipoVideoidTipoVideo.getClass(), tipoVideoidTipoVideo.getIdTipoVideo());
                infoVideo.setTipoVideoidTipoVideo(tipoVideoidTipoVideo);
            }
            Clasificacion clasificacionidClasificacion = infoVideo.getClasificacionidClasificacion();
            if (clasificacionidClasificacion != null) {
                clasificacionidClasificacion = em.getReference(clasificacionidClasificacion.getClass(), clasificacionidClasificacion.getIdClasificacion());
                infoVideo.setClasificacionidClasificacion(clasificacionidClasificacion);
            }
            Collection<Persona> attachedPersonaCollection = new ArrayList<Persona>();
            for (Persona personaCollectionPersonaToAttach : infoVideo.getPersonaCollection()) {
                personaCollectionPersonaToAttach = em.getReference(personaCollectionPersonaToAttach.getClass(), personaCollectionPersonaToAttach.getIdPersona());
                attachedPersonaCollection.add(personaCollectionPersonaToAttach);
            }
            infoVideo.setPersonaCollection(attachedPersonaCollection);
            Collection<GenerohasInfoVideo> attachedGenerohasInfoVideoCollection = new ArrayList<GenerohasInfoVideo>();
            for (GenerohasInfoVideo generohasInfoVideoCollectionGenerohasInfoVideoToAttach : infoVideo.getGenerohasInfoVideoCollection()) {
                generohasInfoVideoCollectionGenerohasInfoVideoToAttach = em.getReference(generohasInfoVideoCollectionGenerohasInfoVideoToAttach.getClass(), generohasInfoVideoCollectionGenerohasInfoVideoToAttach.getIdGenerohasInfoVideo());
                attachedGenerohasInfoVideoCollection.add(generohasInfoVideoCollectionGenerohasInfoVideoToAttach);
            }
            infoVideo.setGenerohasInfoVideoCollection(attachedGenerohasInfoVideoCollection);
            Collection<CarritohasInfoVideo> attachedCarritohasInfoVideoCollection = new ArrayList<CarritohasInfoVideo>();
            for (CarritohasInfoVideo carritohasInfoVideoCollectionCarritohasInfoVideoToAttach : infoVideo.getCarritohasInfoVideoCollection()) {
                carritohasInfoVideoCollectionCarritohasInfoVideoToAttach = em.getReference(carritohasInfoVideoCollectionCarritohasInfoVideoToAttach.getClass(), carritohasInfoVideoCollectionCarritohasInfoVideoToAttach.getIdCarritohasInfoVideo());
                attachedCarritohasInfoVideoCollection.add(carritohasInfoVideoCollectionCarritohasInfoVideoToAttach);
            }
            infoVideo.setCarritohasInfoVideoCollection(attachedCarritohasInfoVideoCollection);
            Collection<Calificacion> attachedCalificacionCollection = new ArrayList<Calificacion>();
            for (Calificacion calificacionCollectionCalificacionToAttach : infoVideo.getCalificacionCollection()) {
                calificacionCollectionCalificacionToAttach = em.getReference(calificacionCollectionCalificacionToAttach.getClass(), calificacionCollectionCalificacionToAttach.getIdCalificacion());
                attachedCalificacionCollection.add(calificacionCollectionCalificacionToAttach);
            }
            infoVideo.setCalificacionCollection(attachedCalificacionCollection);
            Collection<Video> attachedVideoCollection = new ArrayList<Video>();
            for (Video videoCollectionVideoToAttach : infoVideo.getVideoCollection()) {
                videoCollectionVideoToAttach = em.getReference(videoCollectionVideoToAttach.getClass(), videoCollectionVideoToAttach.getIdVideo());
                attachedVideoCollection.add(videoCollectionVideoToAttach);
            }
            infoVideo.setVideoCollection(attachedVideoCollection);
            em.persist(infoVideo);
            if (tipoVideoidTipoVideo != null) {
                tipoVideoidTipoVideo.getInfoVideoCollection().add(infoVideo);
                tipoVideoidTipoVideo = em.merge(tipoVideoidTipoVideo);
            }
            if (clasificacionidClasificacion != null) {
                clasificacionidClasificacion.getInfoVideoCollection().add(infoVideo);
                clasificacionidClasificacion = em.merge(clasificacionidClasificacion);
            }
            for (Persona personaCollectionPersona : infoVideo.getPersonaCollection()) {
                InfoVideo oldInfoVideoidInfoVideoOfPersonaCollectionPersona = personaCollectionPersona.getInfoVideoidInfoVideo();
                personaCollectionPersona.setInfoVideoidInfoVideo(infoVideo);
                personaCollectionPersona = em.merge(personaCollectionPersona);
                if (oldInfoVideoidInfoVideoOfPersonaCollectionPersona != null) {
                    oldInfoVideoidInfoVideoOfPersonaCollectionPersona.getPersonaCollection().remove(personaCollectionPersona);
                    oldInfoVideoidInfoVideoOfPersonaCollectionPersona = em.merge(oldInfoVideoidInfoVideoOfPersonaCollectionPersona);
                }
            }
            for (GenerohasInfoVideo generohasInfoVideoCollectionGenerohasInfoVideo : infoVideo.getGenerohasInfoVideoCollection()) {
                InfoVideo oldInfoVideoidInfoVideoOfGenerohasInfoVideoCollectionGenerohasInfoVideo = generohasInfoVideoCollectionGenerohasInfoVideo.getInfoVideoidInfoVideo();
                generohasInfoVideoCollectionGenerohasInfoVideo.setInfoVideoidInfoVideo(infoVideo);
                generohasInfoVideoCollectionGenerohasInfoVideo = em.merge(generohasInfoVideoCollectionGenerohasInfoVideo);
                if (oldInfoVideoidInfoVideoOfGenerohasInfoVideoCollectionGenerohasInfoVideo != null) {
                    oldInfoVideoidInfoVideoOfGenerohasInfoVideoCollectionGenerohasInfoVideo.getGenerohasInfoVideoCollection().remove(generohasInfoVideoCollectionGenerohasInfoVideo);
                    oldInfoVideoidInfoVideoOfGenerohasInfoVideoCollectionGenerohasInfoVideo = em.merge(oldInfoVideoidInfoVideoOfGenerohasInfoVideoCollectionGenerohasInfoVideo);
                }
            }
            for (CarritohasInfoVideo carritohasInfoVideoCollectionCarritohasInfoVideo : infoVideo.getCarritohasInfoVideoCollection()) {
                InfoVideo oldInfoVideoidInfoVideoOfCarritohasInfoVideoCollectionCarritohasInfoVideo = carritohasInfoVideoCollectionCarritohasInfoVideo.getInfoVideoidInfoVideo();
                carritohasInfoVideoCollectionCarritohasInfoVideo.setInfoVideoidInfoVideo(infoVideo);
                carritohasInfoVideoCollectionCarritohasInfoVideo = em.merge(carritohasInfoVideoCollectionCarritohasInfoVideo);
                if (oldInfoVideoidInfoVideoOfCarritohasInfoVideoCollectionCarritohasInfoVideo != null) {
                    oldInfoVideoidInfoVideoOfCarritohasInfoVideoCollectionCarritohasInfoVideo.getCarritohasInfoVideoCollection().remove(carritohasInfoVideoCollectionCarritohasInfoVideo);
                    oldInfoVideoidInfoVideoOfCarritohasInfoVideoCollectionCarritohasInfoVideo = em.merge(oldInfoVideoidInfoVideoOfCarritohasInfoVideoCollectionCarritohasInfoVideo);
                }
            }
            for (Calificacion calificacionCollectionCalificacion : infoVideo.getCalificacionCollection()) {
                InfoVideo oldInfoVideoidInfoVideoOfCalificacionCollectionCalificacion = calificacionCollectionCalificacion.getInfoVideoidInfoVideo();
                calificacionCollectionCalificacion.setInfoVideoidInfoVideo(infoVideo);
                calificacionCollectionCalificacion = em.merge(calificacionCollectionCalificacion);
                if (oldInfoVideoidInfoVideoOfCalificacionCollectionCalificacion != null) {
                    oldInfoVideoidInfoVideoOfCalificacionCollectionCalificacion.getCalificacionCollection().remove(calificacionCollectionCalificacion);
                    oldInfoVideoidInfoVideoOfCalificacionCollectionCalificacion = em.merge(oldInfoVideoidInfoVideoOfCalificacionCollectionCalificacion);
                }
            }
            for (Video videoCollectionVideo : infoVideo.getVideoCollection()) {
                InfoVideo oldInfoVideoidInfoVideoOfVideoCollectionVideo = videoCollectionVideo.getInfoVideoidInfoVideo();
                videoCollectionVideo.setInfoVideoidInfoVideo(infoVideo);
                videoCollectionVideo = em.merge(videoCollectionVideo);
                if (oldInfoVideoidInfoVideoOfVideoCollectionVideo != null) {
                    oldInfoVideoidInfoVideoOfVideoCollectionVideo.getVideoCollection().remove(videoCollectionVideo);
                    oldInfoVideoidInfoVideoOfVideoCollectionVideo = em.merge(oldInfoVideoidInfoVideoOfVideoCollectionVideo);
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

    public void edit(InfoVideo infoVideo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            InfoVideo persistentInfoVideo = em.find(InfoVideo.class, infoVideo.getIdInfoVideo());
            TipoVideo tipoVideoidTipoVideoOld = persistentInfoVideo.getTipoVideoidTipoVideo();
            TipoVideo tipoVideoidTipoVideoNew = infoVideo.getTipoVideoidTipoVideo();
            Clasificacion clasificacionidClasificacionOld = persistentInfoVideo.getClasificacionidClasificacion();
            Clasificacion clasificacionidClasificacionNew = infoVideo.getClasificacionidClasificacion();
            Collection<Persona> personaCollectionOld = persistentInfoVideo.getPersonaCollection();
            Collection<Persona> personaCollectionNew = infoVideo.getPersonaCollection();
            Collection<GenerohasInfoVideo> generohasInfoVideoCollectionOld = persistentInfoVideo.getGenerohasInfoVideoCollection();
            Collection<GenerohasInfoVideo> generohasInfoVideoCollectionNew = infoVideo.getGenerohasInfoVideoCollection();
            Collection<CarritohasInfoVideo> carritohasInfoVideoCollectionOld = persistentInfoVideo.getCarritohasInfoVideoCollection();
            Collection<CarritohasInfoVideo> carritohasInfoVideoCollectionNew = infoVideo.getCarritohasInfoVideoCollection();
            Collection<Calificacion> calificacionCollectionOld = persistentInfoVideo.getCalificacionCollection();
            Collection<Calificacion> calificacionCollectionNew = infoVideo.getCalificacionCollection();
            Collection<Video> videoCollectionOld = persistentInfoVideo.getVideoCollection();
            Collection<Video> videoCollectionNew = infoVideo.getVideoCollection();
            List<String> illegalOrphanMessages = null;
            for (Persona personaCollectionOldPersona : personaCollectionOld) {
                if (!personaCollectionNew.contains(personaCollectionOldPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Persona " + personaCollectionOldPersona + " since its infoVideoidInfoVideo field is not nullable.");
                }
            }
            for (GenerohasInfoVideo generohasInfoVideoCollectionOldGenerohasInfoVideo : generohasInfoVideoCollectionOld) {
                if (!generohasInfoVideoCollectionNew.contains(generohasInfoVideoCollectionOldGenerohasInfoVideo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GenerohasInfoVideo " + generohasInfoVideoCollectionOldGenerohasInfoVideo + " since its infoVideoidInfoVideo field is not nullable.");
                }
            }
            for (CarritohasInfoVideo carritohasInfoVideoCollectionOldCarritohasInfoVideo : carritohasInfoVideoCollectionOld) {
                if (!carritohasInfoVideoCollectionNew.contains(carritohasInfoVideoCollectionOldCarritohasInfoVideo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CarritohasInfoVideo " + carritohasInfoVideoCollectionOldCarritohasInfoVideo + " since its infoVideoidInfoVideo field is not nullable.");
                }
            }
            for (Calificacion calificacionCollectionOldCalificacion : calificacionCollectionOld) {
                if (!calificacionCollectionNew.contains(calificacionCollectionOldCalificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Calificacion " + calificacionCollectionOldCalificacion + " since its infoVideoidInfoVideo field is not nullable.");
                }
            }
            for (Video videoCollectionOldVideo : videoCollectionOld) {
                if (!videoCollectionNew.contains(videoCollectionOldVideo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Video " + videoCollectionOldVideo + " since its infoVideoidInfoVideo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoVideoidTipoVideoNew != null) {
                tipoVideoidTipoVideoNew = em.getReference(tipoVideoidTipoVideoNew.getClass(), tipoVideoidTipoVideoNew.getIdTipoVideo());
                infoVideo.setTipoVideoidTipoVideo(tipoVideoidTipoVideoNew);
            }
            if (clasificacionidClasificacionNew != null) {
                clasificacionidClasificacionNew = em.getReference(clasificacionidClasificacionNew.getClass(), clasificacionidClasificacionNew.getIdClasificacion());
                infoVideo.setClasificacionidClasificacion(clasificacionidClasificacionNew);
            }
            Collection<Persona> attachedPersonaCollectionNew = new ArrayList<Persona>();
            for (Persona personaCollectionNewPersonaToAttach : personaCollectionNew) {
                personaCollectionNewPersonaToAttach = em.getReference(personaCollectionNewPersonaToAttach.getClass(), personaCollectionNewPersonaToAttach.getIdPersona());
                attachedPersonaCollectionNew.add(personaCollectionNewPersonaToAttach);
            }
            personaCollectionNew = attachedPersonaCollectionNew;
            infoVideo.setPersonaCollection(personaCollectionNew);
            Collection<GenerohasInfoVideo> attachedGenerohasInfoVideoCollectionNew = new ArrayList<GenerohasInfoVideo>();
            for (GenerohasInfoVideo generohasInfoVideoCollectionNewGenerohasInfoVideoToAttach : generohasInfoVideoCollectionNew) {
                generohasInfoVideoCollectionNewGenerohasInfoVideoToAttach = em.getReference(generohasInfoVideoCollectionNewGenerohasInfoVideoToAttach.getClass(), generohasInfoVideoCollectionNewGenerohasInfoVideoToAttach.getIdGenerohasInfoVideo());
                attachedGenerohasInfoVideoCollectionNew.add(generohasInfoVideoCollectionNewGenerohasInfoVideoToAttach);
            }
            generohasInfoVideoCollectionNew = attachedGenerohasInfoVideoCollectionNew;
            infoVideo.setGenerohasInfoVideoCollection(generohasInfoVideoCollectionNew);
            Collection<CarritohasInfoVideo> attachedCarritohasInfoVideoCollectionNew = new ArrayList<CarritohasInfoVideo>();
            for (CarritohasInfoVideo carritohasInfoVideoCollectionNewCarritohasInfoVideoToAttach : carritohasInfoVideoCollectionNew) {
                carritohasInfoVideoCollectionNewCarritohasInfoVideoToAttach = em.getReference(carritohasInfoVideoCollectionNewCarritohasInfoVideoToAttach.getClass(), carritohasInfoVideoCollectionNewCarritohasInfoVideoToAttach.getIdCarritohasInfoVideo());
                attachedCarritohasInfoVideoCollectionNew.add(carritohasInfoVideoCollectionNewCarritohasInfoVideoToAttach);
            }
            carritohasInfoVideoCollectionNew = attachedCarritohasInfoVideoCollectionNew;
            infoVideo.setCarritohasInfoVideoCollection(carritohasInfoVideoCollectionNew);
            Collection<Calificacion> attachedCalificacionCollectionNew = new ArrayList<Calificacion>();
            for (Calificacion calificacionCollectionNewCalificacionToAttach : calificacionCollectionNew) {
                calificacionCollectionNewCalificacionToAttach = em.getReference(calificacionCollectionNewCalificacionToAttach.getClass(), calificacionCollectionNewCalificacionToAttach.getIdCalificacion());
                attachedCalificacionCollectionNew.add(calificacionCollectionNewCalificacionToAttach);
            }
            calificacionCollectionNew = attachedCalificacionCollectionNew;
            infoVideo.setCalificacionCollection(calificacionCollectionNew);
            Collection<Video> attachedVideoCollectionNew = new ArrayList<Video>();
            for (Video videoCollectionNewVideoToAttach : videoCollectionNew) {
                videoCollectionNewVideoToAttach = em.getReference(videoCollectionNewVideoToAttach.getClass(), videoCollectionNewVideoToAttach.getIdVideo());
                attachedVideoCollectionNew.add(videoCollectionNewVideoToAttach);
            }
            videoCollectionNew = attachedVideoCollectionNew;
            infoVideo.setVideoCollection(videoCollectionNew);
            infoVideo = em.merge(infoVideo);
            if (tipoVideoidTipoVideoOld != null && !tipoVideoidTipoVideoOld.equals(tipoVideoidTipoVideoNew)) {
                tipoVideoidTipoVideoOld.getInfoVideoCollection().remove(infoVideo);
                tipoVideoidTipoVideoOld = em.merge(tipoVideoidTipoVideoOld);
            }
            if (tipoVideoidTipoVideoNew != null && !tipoVideoidTipoVideoNew.equals(tipoVideoidTipoVideoOld)) {
                tipoVideoidTipoVideoNew.getInfoVideoCollection().add(infoVideo);
                tipoVideoidTipoVideoNew = em.merge(tipoVideoidTipoVideoNew);
            }
            if (clasificacionidClasificacionOld != null && !clasificacionidClasificacionOld.equals(clasificacionidClasificacionNew)) {
                clasificacionidClasificacionOld.getInfoVideoCollection().remove(infoVideo);
                clasificacionidClasificacionOld = em.merge(clasificacionidClasificacionOld);
            }
            if (clasificacionidClasificacionNew != null && !clasificacionidClasificacionNew.equals(clasificacionidClasificacionOld)) {
                clasificacionidClasificacionNew.getInfoVideoCollection().add(infoVideo);
                clasificacionidClasificacionNew = em.merge(clasificacionidClasificacionNew);
            }
            for (Persona personaCollectionNewPersona : personaCollectionNew) {
                if (!personaCollectionOld.contains(personaCollectionNewPersona)) {
                    InfoVideo oldInfoVideoidInfoVideoOfPersonaCollectionNewPersona = personaCollectionNewPersona.getInfoVideoidInfoVideo();
                    personaCollectionNewPersona.setInfoVideoidInfoVideo(infoVideo);
                    personaCollectionNewPersona = em.merge(personaCollectionNewPersona);
                    if (oldInfoVideoidInfoVideoOfPersonaCollectionNewPersona != null && !oldInfoVideoidInfoVideoOfPersonaCollectionNewPersona.equals(infoVideo)) {
                        oldInfoVideoidInfoVideoOfPersonaCollectionNewPersona.getPersonaCollection().remove(personaCollectionNewPersona);
                        oldInfoVideoidInfoVideoOfPersonaCollectionNewPersona = em.merge(oldInfoVideoidInfoVideoOfPersonaCollectionNewPersona);
                    }
                }
            }
            for (GenerohasInfoVideo generohasInfoVideoCollectionNewGenerohasInfoVideo : generohasInfoVideoCollectionNew) {
                if (!generohasInfoVideoCollectionOld.contains(generohasInfoVideoCollectionNewGenerohasInfoVideo)) {
                    InfoVideo oldInfoVideoidInfoVideoOfGenerohasInfoVideoCollectionNewGenerohasInfoVideo = generohasInfoVideoCollectionNewGenerohasInfoVideo.getInfoVideoidInfoVideo();
                    generohasInfoVideoCollectionNewGenerohasInfoVideo.setInfoVideoidInfoVideo(infoVideo);
                    generohasInfoVideoCollectionNewGenerohasInfoVideo = em.merge(generohasInfoVideoCollectionNewGenerohasInfoVideo);
                    if (oldInfoVideoidInfoVideoOfGenerohasInfoVideoCollectionNewGenerohasInfoVideo != null && !oldInfoVideoidInfoVideoOfGenerohasInfoVideoCollectionNewGenerohasInfoVideo.equals(infoVideo)) {
                        oldInfoVideoidInfoVideoOfGenerohasInfoVideoCollectionNewGenerohasInfoVideo.getGenerohasInfoVideoCollection().remove(generohasInfoVideoCollectionNewGenerohasInfoVideo);
                        oldInfoVideoidInfoVideoOfGenerohasInfoVideoCollectionNewGenerohasInfoVideo = em.merge(oldInfoVideoidInfoVideoOfGenerohasInfoVideoCollectionNewGenerohasInfoVideo);
                    }
                }
            }
            for (CarritohasInfoVideo carritohasInfoVideoCollectionNewCarritohasInfoVideo : carritohasInfoVideoCollectionNew) {
                if (!carritohasInfoVideoCollectionOld.contains(carritohasInfoVideoCollectionNewCarritohasInfoVideo)) {
                    InfoVideo oldInfoVideoidInfoVideoOfCarritohasInfoVideoCollectionNewCarritohasInfoVideo = carritohasInfoVideoCollectionNewCarritohasInfoVideo.getInfoVideoidInfoVideo();
                    carritohasInfoVideoCollectionNewCarritohasInfoVideo.setInfoVideoidInfoVideo(infoVideo);
                    carritohasInfoVideoCollectionNewCarritohasInfoVideo = em.merge(carritohasInfoVideoCollectionNewCarritohasInfoVideo);
                    if (oldInfoVideoidInfoVideoOfCarritohasInfoVideoCollectionNewCarritohasInfoVideo != null && !oldInfoVideoidInfoVideoOfCarritohasInfoVideoCollectionNewCarritohasInfoVideo.equals(infoVideo)) {
                        oldInfoVideoidInfoVideoOfCarritohasInfoVideoCollectionNewCarritohasInfoVideo.getCarritohasInfoVideoCollection().remove(carritohasInfoVideoCollectionNewCarritohasInfoVideo);
                        oldInfoVideoidInfoVideoOfCarritohasInfoVideoCollectionNewCarritohasInfoVideo = em.merge(oldInfoVideoidInfoVideoOfCarritohasInfoVideoCollectionNewCarritohasInfoVideo);
                    }
                }
            }
            for (Calificacion calificacionCollectionNewCalificacion : calificacionCollectionNew) {
                if (!calificacionCollectionOld.contains(calificacionCollectionNewCalificacion)) {
                    InfoVideo oldInfoVideoidInfoVideoOfCalificacionCollectionNewCalificacion = calificacionCollectionNewCalificacion.getInfoVideoidInfoVideo();
                    calificacionCollectionNewCalificacion.setInfoVideoidInfoVideo(infoVideo);
                    calificacionCollectionNewCalificacion = em.merge(calificacionCollectionNewCalificacion);
                    if (oldInfoVideoidInfoVideoOfCalificacionCollectionNewCalificacion != null && !oldInfoVideoidInfoVideoOfCalificacionCollectionNewCalificacion.equals(infoVideo)) {
                        oldInfoVideoidInfoVideoOfCalificacionCollectionNewCalificacion.getCalificacionCollection().remove(calificacionCollectionNewCalificacion);
                        oldInfoVideoidInfoVideoOfCalificacionCollectionNewCalificacion = em.merge(oldInfoVideoidInfoVideoOfCalificacionCollectionNewCalificacion);
                    }
                }
            }
            for (Video videoCollectionNewVideo : videoCollectionNew) {
                if (!videoCollectionOld.contains(videoCollectionNewVideo)) {
                    InfoVideo oldInfoVideoidInfoVideoOfVideoCollectionNewVideo = videoCollectionNewVideo.getInfoVideoidInfoVideo();
                    videoCollectionNewVideo.setInfoVideoidInfoVideo(infoVideo);
                    videoCollectionNewVideo = em.merge(videoCollectionNewVideo);
                    if (oldInfoVideoidInfoVideoOfVideoCollectionNewVideo != null && !oldInfoVideoidInfoVideoOfVideoCollectionNewVideo.equals(infoVideo)) {
                        oldInfoVideoidInfoVideoOfVideoCollectionNewVideo.getVideoCollection().remove(videoCollectionNewVideo);
                        oldInfoVideoidInfoVideoOfVideoCollectionNewVideo = em.merge(oldInfoVideoidInfoVideoOfVideoCollectionNewVideo);
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
                Integer id = infoVideo.getIdInfoVideo();
                if (findInfoVideo(id) == null) {
                    throw new NonexistentEntityException("The infoVideo with id " + id + " no longer exists.");
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
            InfoVideo infoVideo;
            try {
                infoVideo = em.getReference(InfoVideo.class, id);
                infoVideo.getIdInfoVideo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The infoVideo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Persona> personaCollectionOrphanCheck = infoVideo.getPersonaCollection();
            for (Persona personaCollectionOrphanCheckPersona : personaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This InfoVideo (" + infoVideo + ") cannot be destroyed since the Persona " + personaCollectionOrphanCheckPersona + " in its personaCollection field has a non-nullable infoVideoidInfoVideo field.");
            }
            Collection<GenerohasInfoVideo> generohasInfoVideoCollectionOrphanCheck = infoVideo.getGenerohasInfoVideoCollection();
            for (GenerohasInfoVideo generohasInfoVideoCollectionOrphanCheckGenerohasInfoVideo : generohasInfoVideoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This InfoVideo (" + infoVideo + ") cannot be destroyed since the GenerohasInfoVideo " + generohasInfoVideoCollectionOrphanCheckGenerohasInfoVideo + " in its generohasInfoVideoCollection field has a non-nullable infoVideoidInfoVideo field.");
            }
            Collection<CarritohasInfoVideo> carritohasInfoVideoCollectionOrphanCheck = infoVideo.getCarritohasInfoVideoCollection();
            for (CarritohasInfoVideo carritohasInfoVideoCollectionOrphanCheckCarritohasInfoVideo : carritohasInfoVideoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This InfoVideo (" + infoVideo + ") cannot be destroyed since the CarritohasInfoVideo " + carritohasInfoVideoCollectionOrphanCheckCarritohasInfoVideo + " in its carritohasInfoVideoCollection field has a non-nullable infoVideoidInfoVideo field.");
            }
            Collection<Calificacion> calificacionCollectionOrphanCheck = infoVideo.getCalificacionCollection();
            for (Calificacion calificacionCollectionOrphanCheckCalificacion : calificacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This InfoVideo (" + infoVideo + ") cannot be destroyed since the Calificacion " + calificacionCollectionOrphanCheckCalificacion + " in its calificacionCollection field has a non-nullable infoVideoidInfoVideo field.");
            }
            Collection<Video> videoCollectionOrphanCheck = infoVideo.getVideoCollection();
            for (Video videoCollectionOrphanCheckVideo : videoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This InfoVideo (" + infoVideo + ") cannot be destroyed since the Video " + videoCollectionOrphanCheckVideo + " in its videoCollection field has a non-nullable infoVideoidInfoVideo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoVideo tipoVideoidTipoVideo = infoVideo.getTipoVideoidTipoVideo();
            if (tipoVideoidTipoVideo != null) {
                tipoVideoidTipoVideo.getInfoVideoCollection().remove(infoVideo);
                tipoVideoidTipoVideo = em.merge(tipoVideoidTipoVideo);
            }
            Clasificacion clasificacionidClasificacion = infoVideo.getClasificacionidClasificacion();
            if (clasificacionidClasificacion != null) {
                clasificacionidClasificacion.getInfoVideoCollection().remove(infoVideo);
                clasificacionidClasificacion = em.merge(clasificacionidClasificacion);
            }
            em.remove(infoVideo);
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

    public List<InfoVideo> findInfoVideoEntities() {
        return findInfoVideoEntities(true, -1, -1);
    }

    public List<InfoVideo> findInfoVideoEntities(int maxResults, int firstResult) {
        return findInfoVideoEntities(false, maxResults, firstResult);
    }

    private List<InfoVideo> findInfoVideoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InfoVideo.class));
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

    public InfoVideo findInfoVideo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InfoVideo.class, id);
        } finally {
            em.close();
        }
    }

    public int getInfoVideoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InfoVideo> rt = cq.from(InfoVideo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
