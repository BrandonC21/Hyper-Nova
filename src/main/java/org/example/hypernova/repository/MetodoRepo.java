package org.example.hypernova.repository;

import org.example.hypernova.model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetodoRepo extends JpaRepository<MetodoPago,Integer> {
}
