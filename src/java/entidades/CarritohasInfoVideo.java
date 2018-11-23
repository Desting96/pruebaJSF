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
import javax.validation.constraints.NotNull;

/**
 *
 * @author desting
 */
@Entity
@Table(name = "Carrito_has_InfoVideo")
@NamedQueries({
    @NamedQuery(name = "CarritohasInfoVideo.findAll", query = "SELECT c FROM CarritohasInfoVideo c")
    , @NamedQuery(name = "CarritohasInfoVideo.findByIdCarritohasInfoVideo", query = "SELECT c FROM CarritohasInfoVideo c WHERE c.idCarritohasInfoVideo = :idCarritohasInfoVideo")
    , @NamedQuery(name = "CarritohasInfoVideo.findByCantidad", query = "SELECT c FROM CarritohasInfoVideo c WHERE c.cantidad = :cantidad")})
public class CarritohasInfoVideo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdCarrito_has_InfoVideo")
    private Integer idCarritohasInfoVideo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumn(name = "Carrito_idCarrito", referencedColumnName = "idCarrito")
    @ManyToOne(optional = false)
    private Carrito carritoidCarrito;
    @JoinColumn(name = "InfoVideo_idInfoVideo", referencedColumnName = "idInfoVideo")
    @ManyToOne(optional = false)
    private InfoVideo infoVideoidInfoVideo;

    public CarritohasInfoVideo() {
    }

    public CarritohasInfoVideo(Integer idCarritohasInfoVideo) {
        this.idCarritohasInfoVideo = idCarritohasInfoVideo;
    }

    public CarritohasInfoVideo(Integer idCarritohasInfoVideo, int cantidad) {
        this.idCarritohasInfoVideo = idCarritohasInfoVideo;
        this.cantidad = cantidad;
    }

    public Integer getIdCarritohasInfoVideo() {
        return idCarritohasInfoVideo;
    }

    public void setIdCarritohasInfoVideo(Integer idCarritohasInfoVideo) {
        this.idCarritohasInfoVideo = idCarritohasInfoVideo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Carrito getCarritoidCarrito() {
        return carritoidCarrito;
    }

    public void setCarritoidCarrito(Carrito carritoidCarrito) {
        this.carritoidCarrito = carritoidCarrito;
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
        hash += (idCarritohasInfoVideo != null ? idCarritohasInfoVideo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarritohasInfoVideo)) {
            return false;
        }
        CarritohasInfoVideo other = (CarritohasInfoVideo) object;
        if ((this.idCarritohasInfoVideo == null && other.idCarritohasInfoVideo != null) || (this.idCarritohasInfoVideo != null && !this.idCarritohasInfoVideo.equals(other.idCarritohasInfoVideo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.CarritohasInfoVideo[ idCarritohasInfoVideo=" + idCarritohasInfoVideo + " ]";
    }
    
}
