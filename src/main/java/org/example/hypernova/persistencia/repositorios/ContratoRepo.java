package org.example.hypernova.persistencia.repositorios;

import java.util.List;

import org.example.hypernova.persistencia.entidades.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepo  extends JpaRepository<Contrato,Integer> {
     List<Contrato> findByVehiculo_IdVehiculo(int idVehiculo);
}
