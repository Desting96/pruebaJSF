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
@Table(name = "TipoVideo")
@NamedQueries({
    @NamedQuery(name = "TipoVideo.findAll", query = "SELECT t FROM TipoVideo t")
    , @NamedQuery(name = "TipoVideo.findByIdTipoVideo", query = "SELECT t FROM TipoVideo t WHERE t.idTipoVideo = :idTipoVideo")
    , @NamedQuery(name = "TipoVideo.findByTipoVideo", query = "SELECT t FROM TipoVideo t WHERE t.tipoVideo = :tipoVideo")})
public class TipoVideo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoVideo")
    private Integer idTipoVideo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipoVideo")
    private String tipoVideo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoVideoidTipoVideo")
    private Collection<InfoVideo> infoVideoCollection;

    public TipoVideo() {
    }

    public TipoVideo(Integer idTipoVideo) {
        this.idTipoVideo = idTipoVideo;
    }

    public TipoVideo(Integer idTipoVideo, String tipoVideo) {
        this.idTipoVideo = idTipoVideo;
        this.tipoVideo = tipoVideo;
    }

    public Integer getIdTipoVideo() {
        return idTipoVideo;
    }

    public void setIdTipoVideo(Integer idTipoVideo) {
        this.idTipoVideo = idTipoVideo;
    }

    public String getTipoVideo() {
        return tipoVideo;
    }

    public void setTipoVideo(String tipoVideo) {
        this.tipoVideo = tipoVideo;
    }

    public Collection<InfoVideo> getInfoVideoCollection() {
        return infoVideoCollection;
    }

    public void setInfoVideoCollection(Collection<InfoVideo> infoVideoCollection) {
        this.infoVideoCollection = infoVideoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoVideo != null ? idTipoVideo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoVideo)) {
            return false;
        }
        TipoVideo other = (TipoVideo) object;
        if ((this.idTipoVideo == null && other.idTipoVideo != null) || (this.idTipoVideo != null && !this.idTipoVideo.equals(other.idTipoVideo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + tipoVideo + "";
    }
    
}
