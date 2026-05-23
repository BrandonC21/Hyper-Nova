package org.example.hypernova.servicios;


import org.example.hypernova.enmus.RolesEmp;
import org.example.hypernova.persistencia.entidades.Direccion;
import org.example.hypernova.persistencia.entidades.Empleado;
import org.example.hypernova.persistencia.repositorios.DireccionRepo;
import org.example.hypernova.persistencia.repositorios.EmpleadoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpleadoServicio implements EmpleadoService{
    @Autowired
    private EmpleadoRepo empleadoRepo;

    private DireccionService direccionService;

    public EmpleadoServicio(DireccionService direccionService) {
        this.direccionService = direccionService;
    }

    @Override
    public Empleado agregarEmpleado(Empleado empleado) {
        Direccion direccion = direccionService.agregarDireccion(empleado.getDireccion());
        empleado.setDireccion(direccion);
        return empleadoRepo.save(empleado);
    }

    @Override
    public Empleado buscarPorId(int id) {
        return empleadoRepo.findById(id).get();
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
