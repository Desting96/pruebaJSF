/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entidades.InfoVideo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author desting
 */
@Stateless
public class InfoVideoFacade extends AbstractFacade<InfoVideo> {

    @PersistenceContext(unitName = "prueba5PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InfoVideoFacade() {
        super(InfoVideo.class);
    }
    
}
