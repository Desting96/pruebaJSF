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
@Table(name = "Video_has_Subtitulo")
@NamedQueries({
    @NamedQuery(name = "VideohasSubtitulo.findAll", query = "SELECT v FROM VideohasSubtitulo v")
    , @NamedQuery(name = "VideohasSubtitulo.findByIdVideohasSubtitulo", query = "SELECT v FROM VideohasSubtitulo v WHERE v.idVideohasSubtitulo = :idVideohasSubtitulo")})
public class VideohasSubtitulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdVideo_has_Subtitulo")
    private Integer idVideohasSubtitulo;
    @JoinColumn(name = "Subtitulo_idSubtitulo", referencedColumnName = "idSubtitulo")
    @ManyToOne(optional = false)
    private Subtitulo subtituloidSubtitulo;
    @JoinColumn(name = "Video_idVideo", referencedColumnName = "idVideo")
    @ManyToOne(optional = false)
    private Video videoidVideo;

    public VideohasSubtitulo() {
    }

    public VideohasSubtitulo(Integer idVideohasSubtitulo) {
        this.idVideohasSubtitulo = idVideohasSubtitulo;
    }

    public Integer getIdVideohasSubtitulo() {
        return idVideohasSubtitulo;
    }

    public void setIdVideohasSubtitulo(Integer idVideohasSubtitulo) {
        this.idVideohasSubtitulo = idVideohasSubtitulo;
    }

    public Subtitulo getSubtituloidSubtitulo() {
        return subtituloidSubtitulo;
    }

    public void setSubtituloidSubtitulo(Subtitulo subtituloidSubtitulo) {
        this.subtituloidSubtitulo = subtituloidSubtitulo;
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
        hash += (idVideohasSubtitulo != null ? idVideohasSubtitulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VideohasSubtitulo)) {
            return false;
        }
        VideohasSubtitulo other = (VideohasSubtitulo) object;
        if ((this.idVideohasSubtitulo == null && other.idVideohasSubtitulo != null) || (this.idVideohasSubtitulo != null && !this.idVideohasSubtitulo.equals(other.idVideohasSubtitulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.VideohasSubtitulo[ idVideohasSubtitulo=" + idVideohasSubtitulo + " ]";
    }
    
}
