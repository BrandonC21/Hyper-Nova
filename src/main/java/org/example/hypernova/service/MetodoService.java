package org.example.hypernova.service;

import org.example.hypernova.model.MetodoPago;

public interface MetodoService {
    MetodoPago agregarMetodo(MetodoPago metodoPago) throws Exception;
    MetodoPago actualizarPago(MetodoPago metodoPago);
}
