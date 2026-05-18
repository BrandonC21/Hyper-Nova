package org.example.hypernova.persistencia.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.example.hypernova.persistencia.entidades.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepo  extends JpaRepository<Contrato,Integer> {
     List<Contrato> findByVehiculo_IdVehiculo(int idVehiculo);
     // Buscasr autos con contratos por fecha de inicio y fecha de fin
     List<Contrato> findByFechaInicioGreaterThanEqualAndFechaFinLessThanEqual(LocalDate fechaInicio, LocalDate fechaFin);
     List<Contrato> findByIdContrato(int idContrato);

}
