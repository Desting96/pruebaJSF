/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entidades.VideohasLenguaje;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author desting
 */
@Stateless
public class VideohasLenguajeFacade extends AbstractFacade<VideohasLenguaje> {

    @PersistenceContext(unitName = "prueba5PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VideohasLenguajeFacade() {
        super(VideohasLenguaje.class);
    }
    
}
