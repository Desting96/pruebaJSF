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
@Table(name = "Genero_has_InfoVideo")
@NamedQueries({
    @NamedQuery(name = "GenerohasInfoVideo.findAll", query = "SELECT g FROM GenerohasInfoVideo g")
    , @NamedQuery(name = "GenerohasInfoVideo.findByIdGenerohasInfoVideo", query = "SELECT g FROM GenerohasInfoVideo g WHERE g.idGenerohasInfoVideo = :idGenerohasInfoVideo")})
public class GenerohasInfoVideo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdGenero_has_InfoVideo")
    private Integer idGenerohasInfoVideo;
    @JoinColumn(name = "Genero_idGenero", referencedColumnName = "idGenero")
    @ManyToOne(optional = false)
    private Genero generoidGenero;
    @JoinColumn(name = "InfoVideo_idInfoVideo", referencedColumnName = "idInfoVideo")
    @ManyToOne(optional = false)
    private InfoVideo infoVideoidInfoVideo;

    public GenerohasInfoVideo() {
    }

    public GenerohasInfoVideo(Integer idGenerohasInfoVideo) {
        this.idGenerohasInfoVideo = idGenerohasInfoVideo;
    }

    public Integer getIdGenerohasInfoVideo() {
        return idGenerohasInfoVideo;
    }

    public void setIdGenerohasInfoVideo(Integer idGenerohasInfoVideo) {
        this.idGenerohasInfoVideo = idGenerohasInfoVideo;
    }

    public Genero getGeneroidGenero() {
        return generoidGenero;
    }

    public void setGeneroidGenero(Genero generoidGenero) {
        this.generoidGenero = generoidGenero;
    }

    public InfoVideo getInfoVideoidInfoVideo() {
        return infoVideoidInfoVideo;
    }

    public void setInfoVideoidInfoVideo(InfoVideo infoVideoidInfoVideo) {
        this.infoVideoidInfoVideo = infoVideoidInfoVideo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenerohasInfoVideo != null ? idGenerohasInfoVideo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenerohasInfoVideo)) {
            return false;
        }
        GenerohasInfoVideo other = (GenerohasInfoVideo) object;
        if ((this.idGenerohasInfoVideo == null && other.idGenerohasInfoVideo != null) || (this.idGenerohasInfoVideo != null && !this.idGenerohasInfoVideo.equals(other.idGenerohasInfoVideo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.GenerohasInfoVideo[ idGenerohasInfoVideo=" + idGenerohasInfoVideo + " ]";
    }
    
}
