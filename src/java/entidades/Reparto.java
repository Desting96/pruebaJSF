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
@Table(name = "Reparto")
@NamedQueries({
    @NamedQuery(name = "Reparto.findAll", query = "SELECT r FROM Reparto r")
    , @NamedQuery(name = "Reparto.findByIdReparto", query = "SELECT r FROM Reparto r WHERE r.idReparto = :idReparto")
    , @NamedQuery(name = "Reparto.findByReparto", query = "SELECT r FROM Reparto r WHERE r.reparto = :reparto")})
public class Reparto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idReparto")
    private Integer idReparto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "reparto")
    private String reparto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repartoidReparto")
    private Collection<Persona> personaCollection;

    public Reparto() {
    }

    public Reparto(Integer idReparto) {
        this.idReparto = idReparto;
    }

    public Reparto(Integer idReparto, String reparto) {
        this.idReparto = idReparto;
        this.reparto = reparto;
    }

    public Integer getIdReparto() {
        return idReparto;
    }

    public void setIdReparto(Integer idReparto) {
        this.idReparto = idReparto;
    }

    public String getReparto() {
        return reparto;
    }

    public void setReparto(String reparto) {
        this.reparto = reparto;
    }

    public Collection<Persona> getPersonaCollection() {
        return personaCollection;
    }

    public void setPersonaCollection(Collection<Persona> personaCollection) {
        this.personaCollection = personaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReparto != null ? idReparto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reparto)) {
            return false;
        }
        Reparto other = (Reparto) object;
        if ((this.idReparto == null && other.idReparto != null) || (this.idReparto != null && !this.idReparto.equals(other.idReparto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Reparto[ idReparto=" + idReparto + " ]";
    }
    
}
