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
public class EmpleadoServicio {
    @Autowired
    private EmpleadoRepo empleadoRepo;
    @Autowired
    private DireccionRepo direccionRepo;
     //Agregar empleado
    public Empleado agregarEmpleado(Empleado empleado) {
        Direccion direccion = direccionRepo.save(empleado.getDireccion());
        empleado.setDireccion(direccion);
        return empleadoRepo.save(empleado);
    }    
    //Verificar si el empleado estaba registrado
    //Queda pendiente actualizar empleado
    //Validar Empleado por email y password

    public Empleado login(String email, String password) {
        Optional<Empleado> empleado = empleadoRepo.findByEmail(email);
        if (empleado.isPresent()) {
            if (empleado.get().getPassword().equals(password)) {
                return empleado.get();
            }
        }
        return null;
    }

    //Buscar empleado por id
    public Empleado buscarPorId(int id) {
        return empleadoRepo.findById(id).get();
    }


}
