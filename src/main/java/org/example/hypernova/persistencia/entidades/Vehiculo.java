package org.example.hypernova.persistencia.entidades;

import jakarta.persistence.*;
import org.example.hypernova.enmus.ClasAutos;
import org.example.hypernova.enmus.EstadoAuto;

@Entity
@Table(name = "Vehiculos")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private int idVehiculo;
    private String marca;
    private String modelo;
    @Enumerated(EnumType.STRING)
    private ClasAutos clasificacion;
    private String version;
    @Column(nullable = false, unique = true)
    private String placa;
    private String color;
    private int anio;
    @Column(name = "costo_dia")
    private double costoDia;
    @Column(name = "url_imagen")
    private String urlImagen;
    @Enumerated(EnumType.STRING)
    private EstadoAuto estado;
    private String transmicion;
    private int ocupantes;
    //Numero de serie debe ser unico
    @Column( name = "num_serie", nullable = false, unique = true)
    private String numSerie;
    //Un vehiculo pertenece a un registro de un vehiculo
    @ManyToOne
    @JoinColumn(name = "id_empleado") // Clave  foranea
    private Empleado empleado;

    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }

    public String getTransmicion() {
        return transmicion;
    }

    public void setTransmicion(String transmicion) {
        this.transmicion = transmicion;
    }

    public int getOcupantes() {
        return ocupantes;
    }

    public void setOcupantes(int ocupantes) {
        this.ocupantes = ocupantes;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public ClasAutos getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(ClasAutos clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getCostoDia() {
        return costoDia;
    }

    public void setCostoDia(double costoDia) {
        this.costoDia = costoDia;
    }

    public EstadoAuto getEstado() {
        return estado;
    }

    public void setEstado(EstadoAuto estado) {
        this.estado = estado;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
