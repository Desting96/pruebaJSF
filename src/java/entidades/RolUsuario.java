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
@Table(name = "RolUsuario")
@NamedQueries({
    @NamedQuery(name = "RolUsuario.findAll", query = "SELECT r FROM RolUsuario r")
    , @NamedQuery(name = "RolUsuario.findByIdRolUsuario", query = "SELECT r FROM RolUsuario r WHERE r.idRolUsuario = :idRolUsuario")
    , @NamedQuery(name = "RolUsuario.findByRolUsuario", query = "SELECT r FROM RolUsuario r WHERE r.rolUsuario = :rolUsuario")})
public class RolUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRolUsuario")
    private Integer idRolUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "rolUsuario")
    private String rolUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolUsuarioidRolUsuario")
    private Collection<Usuario> usuarioCollection;

    public RolUsuario() {
    }

    public RolUsuario(Integer idRolUsuario) {
        this.idRolUsuario = idRolUsuario;
    }

    public RolUsuario(Integer idRolUsuario, String rolUsuario) {
        this.idRolUsuario = idRolUsuario;
        this.rolUsuario = rolUsuario;
    }

    public Integer getIdRolUsuario() {
        return idRolUsuario;
    }

    public void setIdRolUsuario(Integer idRolUsuario) {
        this.idRolUsuario = idRolUsuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolUsuario != null ? idRolUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolUsuario)) {
            return false;
        }
        RolUsuario other = (RolUsuario) object;
        if ((this.idRolUsuario == null && other.idRolUsuario != null) || (this.idRolUsuario != null && !this.idRolUsuario.equals(other.idRolUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + rolUsuario + "";
    }
    
}
