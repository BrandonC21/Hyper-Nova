package org.example.hypernova.servicios;

import org.example.hypernova.persistencia.entidades.MetodoPago;

public interface MetodoService {
    MetodoPago agregarMetodo(MetodoPago metodoPago) throws Exception;
    MetodoPago actualizarPago(MetodoPago metodoPago);
}
