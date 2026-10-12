package org.example.hypernova.repository;

import org.example.hypernova.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehiculoRepo extends JpaRepository<Vehiculo,Integer> {
    //Buscar por numero de serie
    Optional<Vehiculo> findByNumSerie(String numSerie);
}
