package org.example.hypernova.persistencia.repositorios;

import org.example.hypernova.persistencia.entidades.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguroRepo extends JpaRepository<Seguro,Integer> {

}
