package org.example.hypernova.servicios;

import org.example.hypernova.persistencia.entidades.Contrato;
import org.example.hypernova.persistencia.entidades.Vehiculo;

import java.util.List;

public interface VehiculoService {
    //Agregar Vehiculo
    Vehiculo agregarVehiculo(Vehiculo vehiculo);
    List<Vehiculo> obtenerVehiculos();
    List<Vehiculo> obtnerList();
    void marcarRentado(int idVehiculo);
    void marcarApartado(int idVehiculo);
    void marcarMantenimiento(int idVehiculo);
    void marcarDisponible(int idVehiculo);
    Vehiculo buscarVehiculo(String numSerie);
    Vehiculo buscarPorIdV(int idVehiculo);
    List<Contrato> busContrato(int idVehiculo);
    String obtenerFecha(int idVehiculo);
    void borrarVehiculo (int idVehiculo)throws Exception;
    List<Vehiculo> buscarVehiculos(String nombre);







}
