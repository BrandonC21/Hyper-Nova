package org.example.hypernova.servicios;


import org.example.hypernova.persistencia.entidades.MetodoPago;
import org.example.hypernova.persistencia.repositorios.MetodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetodoServicio {
    @Autowired
    private MetodoRepo metodoRepo;
    //Agregar metodo de pago
    public MetodoPago agregarMetodo(MetodoPago metodo) {
        try {
            return metodoRepo.save(metodo);
        } catch (Exception e) {
            System.err.println("Error al agregar el método de pago: " + e.getMessage());
            return null;
        }
    }
}
