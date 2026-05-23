package org.example.hypernova.servicios;

import org.example.hypernova.persistencia.entidades.Contrato;
import org.example.hypernova.persistencia.entidades.Vehiculo;

import java.time.LocalDate;
import java.util.List;

public interface ContratoService {
    Contrato crearContrato(Contrato contrato);
    double calcularDeposito(Vehiculo vehiculo, long diasRenta);
    Contrato finalizarContrato(int idContrato, String observaciones, double montoExtra, String descripcionExtra);
    Contrato cancelarContrato(int idContrato) throws Exception;
    Contrato buscarPorId(int idContrato);
    List<Contrato> obtenerContratosPorVehiculo(int idVehiculo);
    int mostrarIdContrato(int idVehiculo)throws Exception;
    void eliminarContratosPorVehiculo(int idVehiculo);
    List<Contrato> obtenerContratosPorFechas(LocalDate fechaInicio, LocalDate fechaFin);
    List<Vehiculo> obtenerVehiculosDisponiblesPorFechas(LocalDate fechaInicio, LocalDate fechaFin);





}
