package org.example.hypernova.persistencia.repositorios;

import org.example.hypernova.persistencia.entidades.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetodoRepo extends JpaRepository<MetodoPago,Integer> {
}
