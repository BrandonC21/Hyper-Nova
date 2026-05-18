package org.example.hypernova.persistencia.repositorios;

import org.example.hypernova.persistencia.entidades.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DireccionRepo extends JpaRepository<Direccion,Integer> {
   
}
