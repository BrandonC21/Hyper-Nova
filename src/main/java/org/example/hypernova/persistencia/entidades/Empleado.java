package org.example.hypernova.persistencia.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.example.hypernova.enmus.RolesEmp;

import java.util.List;

@Entity
@Table(name = "Empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private int idEmpleado;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String celular;
    @Column(nullable = false, unique = true)
    private String email;
    //Roles quedan pendientes
    private String password;
    //Relacion con la direcion
    @Enumerated(EnumType.STRING)
    private RolesEmp rol;
    @ManyToOne
    @JoinColumn(name = "id_direccion")
    private Direccion direccion;

    //Un empleado puede registrar un 1 vehiculo a muchos vehiculos
    @OneToMany(mappedBy = "empleado",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Vehiculo> vehiculos;

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public RolesEmp getRol() {
        return rol;
    }

    public void setRol(RolesEmp rol) {
        this.rol = rol;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
}
