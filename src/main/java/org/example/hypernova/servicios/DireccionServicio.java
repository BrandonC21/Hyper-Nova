package org.example.hypernova.servicios;

import org.example.hypernova.persistencia.entidades.Direccion;
import org.example.hypernova.persistencia.repositorios.DireccionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DireccionServicio {

    @Autowired
    private DireccionRepo direccionRepo;

    //Agregar la direccion
    public Direccion agregarDireccion(Direccion direccion)throws Exception{
        try {
            return direccionRepo.save(direccion);
        } catch (Exception e) {
            throw new Exception("Error al guardar la dirección: " + e.getMessage());
        } 
    }

    //Busca una direccion por su ID
    public Direccion buscarPorId(int idDireccion) throws Exception {
        try {
            return direccionRepo.findById(idDireccion).orElse(null);
        } catch (Exception e) {
            throw new Exception("Error al buscar la dirección: " + e.getMessage());
        }
    }

   
   
}