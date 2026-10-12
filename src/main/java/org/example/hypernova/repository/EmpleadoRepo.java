package org.example.hypernova.repository;

import org.example.hypernova.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepo extends JpaRepository<Empleado,Integer> {
    Optional<Empleado> findByEmail(String email);
}
