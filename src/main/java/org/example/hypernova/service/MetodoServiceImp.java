package org.example.hypernova.service;


import org.example.hypernova.model.MetodoPago;
import org.example.hypernova.repository.MetodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetodoServiceImp implements MetodoService{
    @Autowired
    private MetodoRepo metodoRepo;
    //Agregar metodo de pago
    @Override
    public MetodoPago agregarMetodo(MetodoPago metodo) throws Exception {
        try {
            return metodoRepo.save(metodo);
        } catch (Exception e) {
            throw new Exception("Error al agregar el método de pago", e);
        }
    }

    @Override
    public MetodoPago actualizarPago(MetodoPago metodoPago) {
        return null;
    }
}
