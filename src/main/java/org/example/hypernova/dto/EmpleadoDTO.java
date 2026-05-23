package org.example.hypernova.dto;

import org.example.hypernova.enmus.RolesEmp;

public class EmpleadoDTO {
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String celular;
    private String email;
    private String password;
    private RolesEmp rolesEmp;

    public EmpleadoDTO(String email, String password) {
        this.email = email;
        this.password = password;
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

    public RolesEmp getRolesEmp() {
        return rolesEmp;
    }

    public void setRolesEmp(RolesEmp rolesEmp) {
        this.rolesEmp = rolesEmp;
    }
}
