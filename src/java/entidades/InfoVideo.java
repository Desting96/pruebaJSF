/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author desting
 */
@Entity
@Table(name = "InfoVideo")
@NamedQueries({
    @NamedQuery(name = "InfoVideo.findAll", query = "SELECT i FROM InfoVideo i")
    , @NamedQuery(name = "InfoVideo.findByIdInfoVideo", query = "SELECT i FROM InfoVideo i WHERE i.idInfoVideo = :idInfoVideo")
    , @NamedQuery(name = "InfoVideo.findByNombreVideo", query = "SELECT i FROM InfoVideo i WHERE i.nombreVideo = :nombreVideo")
    , @NamedQuery(name = "InfoVideo.findByImagen", query = "SELECT i FROM InfoVideo i WHERE i.imagen = :imagen")
    , @NamedQuery(name = "InfoVideo.findByPrecio", query = "SELECT i FROM InfoVideo i WHERE i.precio = :precio")
    , @NamedQuery(name = "InfoVideo.findByPersonaje", query = "SELECT i FROM InfoVideo i WHERE i.personaje = :personaje")})
public class InfoVideo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idInfoVideo")
    private Integer idInfoVideo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreVideo")
    private String nombreVideo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "imagen")
    private String imagen;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "precio")
    private String precio;
    @Size(max = 45)
    @Column(name = "personaje")
    private String personaje;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "infoVideoidInfoVideo")
    private Collection<Persona> personaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "infoVideoidInfoVideo")
    private Collection<GenerohasInfoVideo> generohasInfoVideoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "infoVideoidInfoVideo")
    private Collection<CarritohasInfoVideo> carritohasInfoVideoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "infoVideoidInfoVideo")
    private Collection<Calificacion> calificacionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "infoVideoidInfoVideo")
    private Collection<Video> videoCollection;
    @JoinColumn(name = "TipoVideo_idTipoVideo", referencedColumnName = "idTipoVideo")
    @ManyToOne(optional = false)
    private TipoVideo tipoVideoidTipoVideo;
    @JoinColumn(name = "Clasificacion_idClasificacion", referencedColumnName = "idClasificacion")
    @ManyToOne(optional = false)
    private Clasificacion clasificacionidClasificacion;

    public InfoVideo() {
    }

    public InfoVideo(Integer idInfoVideo) {
        this.idInfoVideo = idInfoVideo;
    }

    public InfoVideo(Integer idInfoVideo, String nombreVideo, String imagen, String precio) {
        this.idInfoVideo = idInfoVideo;
        this.nombreVideo = nombreVideo;
        this.imagen = imagen;
        this.precio = precio;
    }

    public Integer getIdInfoVideo() {
        return idInfoVideo;
    }

    public void setIdInfoVideo(Integer idInfoVideo) {
        this.idInfoVideo = idInfoVideo;
    }

    public String getNombreVideo() {
        return nombreVideo;
    }

    public void setNombreVideo(String nombreVideo) {
        this.nombreVideo = nombreVideo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getPersonaje() {
        return personaje;
    }

    public void setPersonaje(String personaje) {
        this.personaje = personaje;
    }

    public Collection<Persona> getPersonaCollection() {
        return personaCollection;
    }

    public void setPersonaCollection(Collection<Persona> personaCollection) {
        this.personaCollection = personaCollection;
    }

    public Collection<GenerohasInfoVideo> getGenerohasInfoVideoCollection() {
        return generohasInfoVideoCollection;
    }

    public void setGenerohasInfoVideoCollection(Collection<GenerohasInfoVideo> generohasInfoVideoCollection) {
        this.generohasInfoVideoCollection = generohasInfoVideoCollection;
    }

    public Collection<CarritohasInfoVideo> getCarritohasInfoVideoCollection() {
        return carritohasInfoVideoCollection;
    }

    public void setCarritohasInfoVideoCollection(Collection<CarritohasInfoVideo> carritohasInfoVideoCollection) {
        this.carritohasInfoVideoCollection = carritohasInfoVideoCollection;
    }

    public Collection<Calificacion> getCalificacionCollection() {
        return calificacionCollection;
    }

    public void setCalificacionCollection(Collection<Calificacion> calificacionCollection) {
        this.calificacionCollection = calificacionCollection;
    }

    public Collection<Video> getVideoCollection() {
        return videoCollection;
    }

    public void setVideoCollection(Collection<Video> videoCollection) {
        this.videoCollection = videoCollection;
    }

    public TipoVideo getTipoVideoidTipoVideo() {
        return tipoVideoidTipoVideo;
    }

    public void setTipoVideoidTipoVideo(TipoVideo tipoVideoidTipoVideo) {
        this.tipoVideoidTipoVideo = tipoVideoidTipoVideo;
    }

    public Clasificacion getClasificacionidClasificacion() {
        return clasificacionidClasificacion;
    }

    public void setClasificacionidClasificacion(Clasificacion clasificacionidClasificacion) {
        this.clasificacionidClasificacion = clasificacionidClasificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInfoVideo != null ? idInfoVideo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InfoVideo)) {
            return false;
        }
        InfoVideo other = (InfoVideo) object;
        if ((this.idInfoVideo == null && other.idInfoVideo != null) || (this.idInfoVideo != null && !this.idInfoVideo.equals(other.idInfoVideo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.InfoVideo[ idInfoVideo=" + idInfoVideo + " ]";
    }
    
}
