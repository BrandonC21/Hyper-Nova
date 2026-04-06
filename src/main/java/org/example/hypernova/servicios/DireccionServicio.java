package org.example.hypernova.servicios;

import org.example.hypernova.persistencia.entidades.Direccion;
import org.example.hypernova.persistencia.repositorios.DireccionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DireccionServicio {

    @Autowired
    private DireccionRepo direccionRepo;

    // 1. Agregar una nueva dirección
    public Direccion agregarDireccion(Direccion direccion) {
        try {
            return direccionRepo.save(direccion);
        } catch (Exception e) {
            System.out.println("Error al guardar la dirección: " + e.getMessage());
            return null; // Retorna nulo para manejar el error en el controlador
        }
    }

    // 2. Buscar dirección por ID
    public Direccion buscarPorId(int idDireccion) {
        try {
            // Usamos Optional.orElse(null) de forma segura
            return direccionRepo.findById(idDireccion).orElse(null);
        } catch (Exception e) {
            System.out.println("Error al buscar la dirección: " + e.getMessage());
            return null;
        }
    }

    // 3. Actualizar una dirección existente
    public Direccion actualizarDireccion(int idDireccion, Direccion nuevosDatos) {
        try {
            Optional<Direccion> dirOpt = direccionRepo.findById(idDireccion);

            if (dirOpt.isPresent()) {
                Direccion existente = dirOpt.get();

                // Actualizamos usando exactamente los campos de tu clase
                existente.setCalle(nuevosDatos.getCalle());
                existente.setColonia(nuevosDatos.getColonia()); // Mantengo "colinia" como lo tienes en tu clase
                existente.setCodigoPostal(nuevosDatos.getCodigoPostal());
                existente.setDelegacion(nuevosDatos.getDelegacion());

                System.out.println("Dirección " + idDireccion + " actualizada correctamente.");
                return direccionRepo.save(existente);
            } else {
                System.out.println("No se encontró la dirección con ID: " + idDireccion);
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar la dirección: " + e.getMessage());
            return null;
        }
    }

    // 4. Eliminar una dirección
    public void borrarDireccion(int idDireccion) {
        try {
            direccionRepo.deleteById(idDireccion);
            System.out.println("Dirección eliminada correctamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar: Es posible que un Cliente esté usando esta dirección. " + e.getMessage());
        }
    }
}