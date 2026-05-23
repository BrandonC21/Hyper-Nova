package org.example.hypernova.servicios;

import org.example.hypernova.enmus.EstadoAuto;
import org.example.hypernova.enmus.EstadoContrato;
import org.example.hypernova.persistencia.entidades.Contrato;
import org.example.hypernova.persistencia.entidades.Vehiculo;
import org.example.hypernova.persistencia.repositorios.VehiculoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculoServicio implements VehiculoService {
    @Autowired
    VehiculoRepo vehiculoRepo;

    private final ContratoService contratoServicio;

    public VehiculoServicio(ContratoService contratoServicio) {
        this.contratoServicio = contratoServicio;
    }


    //Agregar Vehiculo
    @Override
    public Vehiculo agregarVehiculo(Vehiculo vehiculo){
        vehiculo.setEstado(EstadoAuto.DISPONIBLE);
        return vehiculoRepo.save(vehiculo);
    }

    //Obtner un lista de los vehiculos
    @Override
    public List<Vehiculo> obtenerVehiculos(){
        return vehiculoRepo.findAll();
    }

    //Obtner vehiculos sin rentar y sin mantenimiento
    @Override
    public List<Vehiculo> obtnerList(){
        //Obtner todos los vehiculos
        List<Vehiculo> vehiculos = obtenerVehiculos();
        //Para los vehiculos que esten disponibles
        List<Vehiculo> vehiculosDisponibles = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getEstado() == EstadoAuto.RENTADO || vehiculo.getEstado() == EstadoAuto.MANTENIMIENTO) {
            }else{
                //Agregamos lops vehiculos disponibles a la lista
                vehiculosDisponibles.add(vehiculo);
            }
        }
        return vehiculosDisponibles;
    }

    //Actualizar un vehiculo si se encuentra en renta
    @Override
    public void marcarRentado(int idVehiculo){
        try {
            Vehiculo vehiculo = vehiculoRepo.findById(idVehiculo).get();
            vehiculo.setEstado(EstadoAuto.RENTADO);
            vehiculoRepo.save(vehiculo);
        }catch (Exception e){
            System.out.println("No existe el vehiculo con el id: "+idVehiculo);
        }
    }

    //Actualizar un vehiculo si se encuentra en apartado
    @Override
    public void marcarApartado(int idVehiculo){
        try {
            Vehiculo vehiculo = vehiculoRepo.findById(idVehiculo).get();
            vehiculo.setEstado(EstadoAuto.APARTADO);
            vehiculoRepo.save(vehiculo);
        }catch (Exception e){
            System.out.println("No existe el vehiculo con el id: "+idVehiculo);
        }
    }

    //Actualizar un vehiculo si se encuentra en mantenimiento
    @Override
    public void marcarMantenimiento(int idVehiculo){
        try {
            Vehiculo vehiculo = vehiculoRepo.findById(idVehiculo).get();
            vehiculo.setEstado(EstadoAuto.MANTENIMIENTO);
            vehiculoRepo.save(vehiculo);
        }catch (Exception e){
            System.out.println("No existe el vehiculo con el id: "+idVehiculo);
        }

    }
    //Marcar disponible cuando el vehiculo cuando acabe el contrato
    @Override
    public void marcarDisponible(int idVehiculo){
        try {
            Vehiculo vehiculo = vehiculoRepo.findById(idVehiculo).get();
            vehiculo.setEstado(EstadoAuto.DISPONIBLE);
            vehiculoRepo.save(vehiculo);
        } catch (Exception e){
            System.out.println("No existe el vehiculo con el id: "+idVehiculo);
        }

    }
    @Override
    public Vehiculo buscarVehiculo(String numSerie) {
        Optional<Vehiculo> vehiculo = vehiculoRepo.findByNumSerie(numSerie);
        if (vehiculo.isPresent()) {
            return vehiculo.get();
        } else  {
            return null;
        }
    }
    @Override
    public Vehiculo buscarPorIdV(int idVehiculo) {
        return vehiculoRepo.findById(idVehiculo).get();
    }
    @Override
    public List<Contrato> busContrato(int idVehiculo){
        return contratoServicio.obtenerContratosPorVehiculo(idVehiculo);
    }

    //Obtener fecha de inicio y fin
    @Override
    public String obtenerFecha(int idVehiculo) {
        List<Contrato> contratos = busContrato(idVehiculo);
        for (Contrato contrato : contratos) {
            if (contrato.getEstadoContrato() == EstadoContrato.RESERVADO) {
                String fechaInicio = contrato.getFechaInicio().toString();
                String fechaFin = contrato.getFechaFin().toString();
                return fechaInicio + " a " + fechaFin;
            }
        }
        return "No hay contratos en curso para este vehículo.";
    }

    //Elimiar vehiculo
    @Override
   public void borrarVehiculo(int idVehiculo) throws Exception {
    Vehiculo vehiculo = buscarPorIdV(idVehiculo);
    if (vehiculo != null) {
        if (vehiculo.getEstado() == EstadoAuto.MANTENIMIENTO) {
            throw new Exception("No se puede eliminar el vehículo porque está en estado de mantenimiento " + vehiculo.getEstado());
        } else {
           List<Contrato> contratos = busContrato(idVehiculo);
           for (Contrato contrato : contratos) {
                if (contrato.getEstadoContrato() == EstadoContrato.EN_CURSO) {
                   throw new Exception("No se puede eliminar el vehículo porque tiene contratos en curso.");
                }
            }
            //Eliminar los contratos asociados al vehículo
            if(!contratos.isEmpty()){
                contratoServicio.eliminarContratosPorVehiculo(idVehiculo);
            }
            vehiculoRepo.delete(vehiculo);
        }
    } else {
        throw new Exception("Vehículo no encontrado");
       }
    }

    //Buscar vehiculos por marca o modelo
    @Override
    public List<Vehiculo> buscarVehiculos(String nombre){
        List<Vehiculo> vehiculosEncontrados = new ArrayList<>();
        List<Vehiculo> vehiculos = obtenerVehiculos();
        for(Vehiculo vehiculo: vehiculos){
            if(vehiculo.getMarca().equalsIgnoreCase(nombre) || vehiculo.getModelo().equalsIgnoreCase(nombre)){
                vehiculosEncontrados.add(vehiculo);
            }
        } 
        return vehiculosEncontrados;
    }

}
