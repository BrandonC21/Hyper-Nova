package org.example.hypernova.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.example.hypernova.persistencia.entidades.Direccion;
import org.example.hypernova.persistencia.repositorios.DireccionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DireccionServicio implements DireccionService{

    @Autowired
    private DireccionRepo direccionRepo;

    //Agregar la direccion
    public Direccion agregarDireccion(Direccion direccion){
        return direccionRepo.save(direccion);
    }

    @Override
    public Direccion actualizarDireccion(Direccion direccion){
            //Primero obtener el id a modificar
            Direccion direcionActual = buscarPorId(direccion.getIdDireccion());
            direcionActual.setCalle(direccion.getCalle());
            direcionActual.setColonia(direccion.getColonia());
            direcionActual.setDelegacion(direccion.getDelegacion());
            direcionActual.setCodigoPostal(direccion.getCodigoPostal());
            return direccionRepo.save(direcionActual);
    }

    //Busca una direccion por su ID
    public Direccion buscarPorId(int idDireccion) {
        return direccionRepo.findById(idDireccion)
                .orElseThrow(() -> new EntityNotFoundException("Direccion no encontrada con el ID: " + idDireccion));
    }

}