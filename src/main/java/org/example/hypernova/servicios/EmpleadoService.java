package org.example.hypernova.servicios;

import org.example.hypernova.persistencia.entidades.Empleado;

public interface EmpleadoService {
    Empleado agregarEmpleado(Empleado empleado);
    Empleado buscarPorId (int id);
    Empleado login (String email, String password);
}
