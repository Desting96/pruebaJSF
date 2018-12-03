package controlador;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.InfoVideoFacade;
import entidades.*;
import java.util.*;

@ManagedBean(name = "beanInfoVideo")
@SessionScoped
public class InfoVideoManagedBean {

    @EJB
    private InfoVideoFacade infoVideoFacade;
    private InfoVideo info= new InfoVideo();

    public InfoVideo getInfo() {
        return info;
    }

    public void setInfo(InfoVideo info) {
        this.info = info;
    }
    public InfoVideoManagedBean() {
    }
    
    public List<InfoVideo> dashsachInfo() {
        return this.infoVideoFacade.findAll();
    }
}
