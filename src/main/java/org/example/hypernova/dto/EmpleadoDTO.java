package org.example.hypernova.dto;

import org.example.hypernova.enums.RolesEmp;

public class EmpleadoDTO {
    private String email;
    private String password;
    private RolesEmp rolesEmp;

    public EmpleadoDTO(String email, String password) {
        this.email = email;
        this.password = password;
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
