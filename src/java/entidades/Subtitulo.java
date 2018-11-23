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
@Table(name = "Subtitulo")
@NamedQueries({
    @NamedQuery(name = "Subtitulo.findAll", query = "SELECT s FROM Subtitulo s")
    , @NamedQuery(name = "Subtitulo.findByIdSubtitulo", query = "SELECT s FROM Subtitulo s WHERE s.idSubtitulo = :idSubtitulo")
    , @NamedQuery(name = "Subtitulo.findBySubtitulo", query = "SELECT s FROM Subtitulo s WHERE s.subtitulo = :subtitulo")})
public class Subtitulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSubtitulo")
    private Integer idSubtitulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "subtitulo")
    private String subtitulo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subtituloidSubtitulo")
    private Collection<VideohasSubtitulo> videohasSubtituloCollection;

    public Subtitulo() {
    }

    public Subtitulo(Integer idSubtitulo) {
        this.idSubtitulo = idSubtitulo;
    }

    public Subtitulo(Integer idSubtitulo, String subtitulo) {
        this.idSubtitulo = idSubtitulo;
        this.subtitulo = subtitulo;
    }

    public Integer getIdSubtitulo() {
        return idSubtitulo;
    }

    public void setIdSubtitulo(Integer idSubtitulo) {
        this.idSubtitulo = idSubtitulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public Collection<VideohasSubtitulo> getVideohasSubtituloCollection() {
        return videohasSubtituloCollection;
    }

    public void setVideohasSubtituloCollection(Collection<VideohasSubtitulo> videohasSubtituloCollection) {
        this.videohasSubtituloCollection = videohasSubtituloCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubtitulo != null ? idSubtitulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subtitulo)) {
            return false;
        }
        Subtitulo other = (Subtitulo) object;
        if ((this.idSubtitulo == null && other.idSubtitulo != null) || (this.idSubtitulo != null && !this.idSubtitulo.equals(other.idSubtitulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Subtitulo[ idSubtitulo=" + idSubtitulo + " ]";
    }
    
}
