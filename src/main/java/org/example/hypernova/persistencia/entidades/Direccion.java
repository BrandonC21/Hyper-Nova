package org.example.hypernova.persistencia.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "Direcciones" )
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private int idDireccion;
    private String calle;
    private String colonia;
    @Column(name = "codigo_postal")
    private Long codigoPostal;
    private String delegacion;

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Long getCodigoPostal() {return codigoPostal;}

    public void setCodigoPostal(Long codigoPostal) { this.codigoPostal = codigoPostal;}

    public String getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }
}
