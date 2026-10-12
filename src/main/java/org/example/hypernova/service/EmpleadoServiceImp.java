package org.example.hypernova.service;


import org.example.hypernova.model.Direccion;
import org.example.hypernova.model.Empleado;
import org.example.hypernova.repository.EmpleadoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpleadoServiceImp implements EmpleadoService{
    @Autowired
    private EmpleadoRepo empleadoRepo;

    private DireccionService direccionService;

    public EmpleadoServiceImp(DireccionService direccionService) {
        this.direccionService = direccionService;
    }

    @Override
    public Empleado agregarEmpleado(Empleado empleado) {
        if(empleado.getDireccion() != null) {
            Direccion direccion = direccionService.agregarDireccion(empleado.getDireccion());
            empleado.setDireccion(direccion);
        }
        return empleadoRepo.save(empleado);
    }

    @Override
    public Empleado buscarPorId(int id) {
        return empleadoRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con el ID: " + id));
    }

    @Override
    public Empleado login(String email, String password) {
        Optional<Empleado> empleado = empleadoRepo.findByEmail(email);
        if (empleado.isPresent()) {
            if(empleado.get().getPassword().equals(password.trim())) {
                return empleado.get();
            }
        }
        return null;
    }

}
