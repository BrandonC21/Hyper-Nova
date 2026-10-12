package org.example.hypernova.service;

import org.example.hypernova.model.Direccion;

public interface DireccionService {
    Direccion agregarDireccion(Direccion direccion);
    Direccion actualizarDireccion(Direccion direccion);
    Direccion buscarPorId(int idDireccion);
}
