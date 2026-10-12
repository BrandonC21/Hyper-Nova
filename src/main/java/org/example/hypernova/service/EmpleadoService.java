package org.example.hypernova.service;

import org.example.hypernova.model.Empleado;

public interface EmpleadoService {
    Empleado agregarEmpleado(Empleado empleado);
    Empleado buscarPorId (int id);
    Empleado login (String email, String password);
}
