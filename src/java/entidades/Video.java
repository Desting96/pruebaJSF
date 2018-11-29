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
@Table(name = "Video")
@NamedQueries({
    @NamedQuery(name = "Video.findAll", query = "SELECT v FROM Video v")
    , @NamedQuery(name = "Video.findByIdVideo", query = "SELECT v FROM Video v WHERE v.idVideo = :idVideo")
    , @NamedQuery(name = "Video.findByNombreCapitulo", query = "SELECT v FROM Video v WHERE v.nombreCapitulo = :nombreCapitulo")
    , @NamedQuery(name = "Video.findByDuracion", query = "SELECT v FROM Video v WHERE v.duracion = :duracion")
    , @NamedQuery(name = "Video.findByVideo", query = "SELECT v FROM Video v WHERE v.video = :video")
    , @NamedQuery(name = "Video.findByIsan", query = "SELECT v FROM Video v WHERE v.isan = :isan")})
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVideo")
    private Integer idVideo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreCapitulo")
    private String nombreCapitulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "duracion")
    private String duracion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "video")
    private String video;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "isan")
    private String isan;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "videoidVideo")
    private Collection<VideohasSubtitulo> videohasSubtituloCollection;
    @JoinColumn(name = "InfoVideo_idInfoVideo", referencedColumnName = "idInfoVideo")
    @ManyToOne(optional = false)
    private InfoVideo infoVideoidInfoVideo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "videoidVideo")
    private Collection<VideohasLenguaje> videohasLenguajeCollection;

    public Video() {
    }

    public Video(Integer idVideo) {
        this.idVideo = idVideo;
    }

    public Video(Integer idVideo, String nombreCapitulo, String duracion, String video, String isan) {
        this.idVideo = idVideo;
        this.nombreCapitulo = nombreCapitulo;
        this.duracion = duracion;
        this.video = video;
        this.isan = isan;
    }

    public Integer getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(Integer idVideo) {
        this.idVideo = idVideo;
    }

    public String getNombreCapitulo() {
        return nombreCapitulo;
    }

    public void setNombreCapitulo(String nombreCapitulo) {
        this.nombreCapitulo = nombreCapitulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getIsan() {
        return isan;
    }

    public void setIsan(String isan) {
        this.isan = isan;
    }

    public Collection<VideohasSubtitulo> getVideohasSubtituloCollection() {
        return videohasSubtituloCollection;
    }

    public void setVideohasSubtituloCollection(Collection<VideohasSubtitulo> videohasSubtituloCollection) {
        this.videohasSubtituloCollection = videohasSubtituloCollection;
    }

    public InfoVideo getInfoVideoidInfoVideo() {
        return infoVideoidInfoVideo;
    }

    public void setInfoVideoidInfoVideo(InfoVideo infoVideoidInfoVideo) {
        this.infoVideoidInfoVideo = infoVideoidInfoVideo;
    }

    public Collection<VideohasLenguaje> getVideohasLenguajeCollection() {
        return videohasLenguajeCollection;
    }

    public void setVideohasLenguajeCollection(Collection<VideohasLenguaje> videohasLenguajeCollection) {
        this.videohasLenguajeCollection = videohasLenguajeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVideo != null ? idVideo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Video)) {
            return false;
        }
        Video other = (Video) object;
        if ((this.idVideo == null && other.idVideo != null) || (this.idVideo != null && !this.idVideo.equals(other.idVideo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + idVideo + "";
    }
    
}
