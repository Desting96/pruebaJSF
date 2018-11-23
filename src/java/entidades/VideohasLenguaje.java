/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author desting
 */
@Entity
@Table(name = "Video_has_Lenguaje")
@NamedQueries({
    @NamedQuery(name = "VideohasLenguaje.findAll", query = "SELECT v FROM VideohasLenguaje v")
    , @NamedQuery(name = "VideohasLenguaje.findByIdVideohasLenguaje", query = "SELECT v FROM VideohasLenguaje v WHERE v.idVideohasLenguaje = :idVideohasLenguaje")})
public class VideohasLenguaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdVideo_has_Lenguaje")
    private Integer idVideohasLenguaje;
    @JoinColumn(name = "Lenguaje_idLenguaje", referencedColumnName = "idLenguaje")
    @ManyToOne(optional = false)
    private Lenguaje lenguajeidLenguaje;
    @JoinColumn(name = "Video_idVideo", referencedColumnName = "idVideo")
    @ManyToOne(optional = false)
    private Video videoidVideo;

    public VideohasLenguaje() {
    }

    public VideohasLenguaje(Integer idVideohasLenguaje) {
        this.idVideohasLenguaje = idVideohasLenguaje;
    }

    public Integer getIdVideohasLenguaje() {
        return idVideohasLenguaje;
    }

    public void setIdVideohasLenguaje(Integer idVideohasLenguaje) {
        this.idVideohasLenguaje = idVideohasLenguaje;
    }

    public Lenguaje getLenguajeidLenguaje() {
        return lenguajeidLenguaje;
    }

    public void setLenguajeidLenguaje(Lenguaje lenguajeidLenguaje) {
        this.lenguajeidLenguaje = lenguajeidLenguaje;
    }

    public Video getVideoidVideo() {
        return videoidVideo;
    }

    public void setVideoidVideo(Video videoidVideo) {
        this.videoidVideo = videoidVideo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVideohasLenguaje != null ? idVideohasLenguaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VideohasLenguaje)) {
            return false;
        }
        VideohasLenguaje other = (VideohasLenguaje) object;
        if ((this.idVideohasLenguaje == null && other.idVideohasLenguaje != null) || (this.idVideohasLenguaje != null && !this.idVideohasLenguaje.equals(other.idVideohasLenguaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.VideohasLenguaje[ idVideohasLenguaje=" + idVideohasLenguaje + " ]";
    }
    
}
