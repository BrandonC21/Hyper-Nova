package org.example.hypernova.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.example.hypernova.enums.EstadoContrato;
import org.example.hypernova.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepo  extends JpaRepository<Contrato,Integer> {
     List<Contrato> findByVehiculo_IdVehiculo(int idVehiculo);
     // Buscasr autos con contratos por fecha de inicio y fecha de fin
     List<Contrato> findByFechaInicioGreaterThanEqualAndFechaFinLessThanEqual(LocalDate fechaInicio, LocalDate fechaFin);
     List<Contrato> findByIdContrato(int idContrato);
     //Validar que el folio sea unico
     boolean existsByFolio(String folio);
     Optional<Contrato> findTopByCliente_IdClienteAndEstadoContratoInOrderByFechaInicioDesc(
             int idCliente,
             List<EstadoContrato> estados
     );

}
