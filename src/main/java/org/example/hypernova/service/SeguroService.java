package org.example.hypernova.service;

import org.example.hypernova.model.Seguro;

public interface SeguroService {
    Seguro agregarSeguro(Seguro seguro) throws Exception;
    double calcularCosto(Seguro seguro);
    Seguro modificarSeguro(Seguro seguroActualizado);
    Seguro buscarPorId(int id);
}
