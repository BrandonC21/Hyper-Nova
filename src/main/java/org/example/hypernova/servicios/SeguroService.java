package org.example.hypernova.servicios;

import org.example.hypernova.persistencia.entidades.Seguro;

public interface SeguroService {
    Seguro agregarSeguro(Seguro seguro) throws Exception;
    double calcularCosto(Seguro seguro);
    Seguro modificarSeguro(int id, Seguro seguroActualizado);
}
