package org.example.hypernova.service;

import jakarta.persistence.EntityNotFoundException;

import org.example.hypernova.model.Direccion;
import org.example.hypernova.repository.DireccionRepo;
import org.springframework.stereotype.Service;

@Service
public class DireccionServiceImp implements DireccionService{

    private final DireccionRepo direccionRepo;

    public DireccionServiceImp(DireccionRepo direccionRepo) {
        this.direccionRepo = direccionRepo;
    }

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