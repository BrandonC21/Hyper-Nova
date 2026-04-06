package org.example.hypernova.persistencia.repositorios;

import org.example.hypernova.persistencia.entidades.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehiculoRepo extends JpaRepository<Vehiculo,Integer> {
    //Buscar por numero de serie
    Optional<Vehiculo> findByNumSerie(String numSerie);
}
