
package controlador;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.VideoFacade;
import entidades.*;
import java.util.*;

@ManagedBean(name="beanVideo")
@SessionScoped
public class videoManagedBean{

    @EJB
    private VideoFacade videoFacade;
    private Video v = new Video();

    public videoManagedBean() {
    }
    
    public List<Video> dashsachVideo() {
        return this.videoFacade.findAll();
    }
    
}
