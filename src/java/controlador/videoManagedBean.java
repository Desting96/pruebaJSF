package controlador;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.VideoFacade;
import entidades.*;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@ManagedBean(name = "beanVideo")
@SessionScoped
public class videoManagedBean {
    Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @EJB
    private VideoFacade videoFacade = new VideoFacade();
//    private Video v = new Video();
   
    public videoManagedBean() {
    }

    public List<Video> dashsachVideo() {
        return this.videoFacade.findAll();
    }
    
    public List<Video> consultarVideo() {
        return this.videoFacade.consultarVideos();
    }
    
    public List<Video> consultarVideoporID() {
        return this.videoFacade.consultarporID(id);
    }
}
