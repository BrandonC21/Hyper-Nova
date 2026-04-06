package org.example.hypernova.persistencia.repositorios;

import org.example.hypernova.persistencia.entidades.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepo extends JpaRepository<Empleado,Integer> {
    Optional<Empleado> findByEmail(String email);
}
