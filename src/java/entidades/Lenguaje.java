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
@Table(name = "Lenguaje")
@NamedQueries({
    @NamedQuery(name = "Lenguaje.findAll", query = "SELECT l FROM Lenguaje l")
    , @NamedQuery(name = "Lenguaje.findByIdLenguaje", query = "SELECT l FROM Lenguaje l WHERE l.idLenguaje = :idLenguaje")
    , @NamedQuery(name = "Lenguaje.findByLenguaje", query = "SELECT l FROM Lenguaje l WHERE l.lenguaje = :lenguaje")})
public class Lenguaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLenguaje")
    private Integer idLenguaje;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "lenguaje")
    private String lenguaje;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lenguajeidLenguaje")
    private Collection<VideohasLenguaje> videohasLenguajeCollection;

    public Lenguaje() {
    }

    public Lenguaje(Integer idLenguaje) {
        this.idLenguaje = idLenguaje;
    }

    public Lenguaje(Integer idLenguaje, String lenguaje) {
        this.idLenguaje = idLenguaje;
        this.lenguaje = lenguaje;
    }

    public Integer getIdLenguaje() {
        return idLenguaje;
    }

    public void setIdLenguaje(Integer idLenguaje) {
        this.idLenguaje = idLenguaje;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
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
        hash += (idLenguaje != null ? idLenguaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lenguaje)) {
            return false;
        }
        Lenguaje other = (Lenguaje) object;
        if ((this.idLenguaje == null && other.idLenguaje != null) || (this.idLenguaje != null && !this.idLenguaje.equals(other.idLenguaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Lenguaje[ idLenguaje=" + idLenguaje + " ]";
    }
    
}
