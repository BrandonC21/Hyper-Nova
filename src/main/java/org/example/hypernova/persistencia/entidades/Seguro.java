package org.example.hypernova.persistencia.entidades;

import jakarta.persistence.*;
import org.example.hypernova.enmus.TipoSeguro;

@Entity
@Table(name = "Seguros")
public class Seguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguro")
    private int idSeguro;
    @Column(name = "nombre_seguro")
    private String nombreAseguradora;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_seguro")
    private TipoSeguro tipoSeguro;
    private double costo;

    public int getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(int idSeguro) {
        this.idSeguro = idSeguro;
    }

    public String getNombreAseguradora() {
        return nombreAseguradora;
    }

    public void setNombreAseguradora(String nombreAseguradora) {
        this.nombreAseguradora = nombreAseguradora;
    }

    public TipoSeguro getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(TipoSeguro tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
}
