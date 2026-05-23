package org.example.hypernova.servicios;

import org.example.hypernova.persistencia.entidades.Direccion;

public interface DireccionService {
    Direccion agregarDireccion(Direccion direccion);
    Direccion actualizarDireccion(Direccion direccion);
    Direccion buscarPorId(int idDireccion);
}
