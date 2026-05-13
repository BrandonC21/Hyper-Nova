package org.example.hypernova.servicios;

import org.example.hypernova.enmus.EstadoContrato;
import org.example.hypernova.persistencia.entidades.Cliente;
import org.example.hypernova.persistencia.entidades.Contrato;
import org.example.hypernova.persistencia.entidades.Extra;
import org.example.hypernova.persistencia.entidades.MetodoPago;
import org.example.hypernova.persistencia.entidades.Seguro;
import org.example.hypernova.persistencia.entidades.Vehiculo;
import org.example.hypernova.persistencia.repositorios.ContratoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoServicio {

    @Autowired
    private ContratoRepo contratoRepo;

    @Autowired
    private VehiculoServicio vehiculoServicio;

    @Autowired
    private SeguroServicio seguroServicio;

    @Autowired
    private MetodoServicio metodoServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    // Crear el contrato e iniciarlo (EN CURSO)
    public Contrato crearContrato(Contrato contrato){
        try{
            //Primer guadrar el estado del contrato
            contrato.setEstadoContrato(EstadoContrato.EN_CURSO);
            //Vicular 
            
            //Guardar los datos de seguro
            if (contrato.getSeguro() != null) {
                Seguro seguroGuardado = seguroServicio.agregarSeguro(contrato.getSeguro());
                contrato.setSeguro(seguroGuardado);
                
            }
            //Guardar los datos del método de pago
            if(contrato.getMetodoPago() != null){
                MetodoPago metodoGuardado = metodoServicio.agregarMetodo(contrato.getMetodoPago());
                contrato.setMetodoPago(metodoGuardado);
            }
            //Guardar al cliente si es nuevo
            if (contrato.getCliente() != null) {
                Cliente existente = clienteServicio.buscarPorId(contrato.getCliente().getIdCliente());
                contrato.setCliente(existente);
               
            } else{
                 Cliente clienteGuardado = clienteServicio.agregarCliente(contrato.getCliente());
                contrato.setCliente(clienteGuardado);
            }

            Vehiculo vehiculo = vehiculoServicio.buscarPorIdV(contrato.getVehiculo().getIdVehiculo());
            long diasRenta = ChronoUnit.DAYS.between(contrato.getFechaInicio(), contrato.getFechaFin());
            contrato.setDeposito(calcularDeposito(vehiculo, diasRenta));
            contrato.setCostoDia(vehiculo.getCostoDia());

            //Vicular el id del empleado quien registro el vehiculo para el contrato
             //contrato.setEmpleado();
            
            // Marcar el vehículo como RENTADO
            vehiculoServicio.marcarRentado(vehiculo.getIdVehiculo());
            return contratoRepo.save(contrato);
        }catch(Exception e){
            throw new RuntimeException("Error creando contrato: " + e.getMessage());
        }
            
    }
  

    public double calcularDeposito(Vehiculo vehiculo, long diasRenta) {
        // El depósito es el 20% por anticipado que son 5 dias 
        double costoEstimado = vehiculo.getCostoDia() * diasRenta; // Asumiendo una renta de 5 días para el cálculo
        return costoEstimado * 0.20; // 20% del costo estimado

    }

    // Finalizar el contrato y calcular el Precio Final
    public Contrato finalizarContrato(int idContrato, String observaciones, double montoExtra, String descripcionExtra) {
        try {
            //Buscamos el contrato por su id
            Optional<Contrato> contratoOpt = contratoRepo.findById(idContrato);
            List<Extra> extras = new ArrayList<>();
            Extra nuevoExtra = new Extra();
            // Si el contrato existe, lo finalizamos
            if (contratoOpt.isPresent()) {
                Contrato contrato = contratoOpt.get();
                // 1. Cambiar estado a FINALIZADO
                contrato.setEstadoContrato(EstadoContrato.FINALIZADO);
                //Agregar observaciones del contrato
                contrato.setObservaciones(observaciones);
                // 2. Calcular los días de renta
                long diasRenta = ChronoUnit.DAYS.between(contrato.getFechaInicio(), contrato.getFechaFin());
                if (diasRenta <= 0) diasRenta = 1; // Cobro mínimo de 1 día

                // 3. Costo base (días * costo por día) y restar el depósito
                double costoTotal = (diasRenta * contrato.getCostoDia()) - contrato.getDeposito();

                // 4. Sumar el costo del seguro (si tiene uno)
                if (contrato.getSeguro() != null) {
                    costoTotal += contrato.getSeguro().getCosto();
                }
                //Crear un exra si la descripcion reporta anomalias o daños al vehículo
                if (descripcionExtra.equals("Daños") || descripcionExtra.equals("Atraso")) {     
                    nuevoExtra.setDescripcion(descripcionExtra);
                    nuevoExtra.setMonto(montoExtra);
                    nuevoExtra.setContrato(contrato);
                    extras.add(nuevoExtra);
                    // Guardar el extra en la base de datos
                    contrato.setExtras(extras);
                } else{
                    nuevoExtra.setDescripcion("Sin daños");
                    nuevoExtra.setMonto(0.0);
                    nuevoExtra.setContrato(contrato);
                    extras.add(nuevoExtra);
                    contrato.setExtras(extras);
                }
                if (contrato.getExtras() != null && !contrato.getExtras().isEmpty()) {
                    for (Extra extra : contrato.getExtras()) {
                        costoTotal += extra.getMonto();
                    }
                }

                // 6. Asignar el costo final
                contrato.setCostoFinal(costoTotal);

                // 7. Liberar el vehículo (Pasa a DISPONIBLE)
                if (contrato.getVehiculo() != null) {
                    vehiculoServicio.marcarDisponible(contrato.getVehiculo().getIdVehiculo());
                }

                System.out.println("Contrato " + idContrato + " finalizado. Total generado: $" + costoTotal);
                return contratoRepo.save(contrato);

            } else {
                System.out.println("No se encontró el contrato con ID: " + idContrato);
                return null;
            }

        } catch (Exception e) {
            System.err.println("Error al finalizar el contrato: " + e.getMessage());
            return null;
        }
    }

    // 3. Cancelar un contrato (En caso de errores o arrepentimiento del cliente)
    public Contrato cancelarContrato(int idContrato) {
        try {
            return contratoRepo.findById(idContrato).map(contrato -> {
                contrato.setEstadoContrato(EstadoContrato.CANCELADO);
                // Liberar vehículo sin cobrar
                if (contrato.getVehiculo() != null) {
                    vehiculoServicio.marcarDisponible(contrato.getVehiculo().getIdVehiculo());
                }
                return contratoRepo.save(contrato);
            }).orElse(null);
        } catch (Exception e) {
            System.err.println("Error al cancelar el contrato: " + e.getMessage());
            return null;
        }
    }

    public Contrato buscarPorId(int idContrato) {
        return contratoRepo.findById(idContrato).get();
    }

     public List<Contrato> obtenerContratosPorVehiculo(int idVehiculo) {
        return contratoRepo.findByVehiculo_IdVehiculo(idVehiculo);
    }

    //Obtner el id del contrato para poder finalizrlo
    public int mostrarIdContrato(int idVehiculo) {
        List<Contrato> contratos = obtenerContratosPorVehiculo(idVehiculo);
        for (Contrato contrato : contratos) {
            if (contrato.getEstadoContrato() == EstadoContrato.EN_CURSO) {
                return contrato.getIdContrato();
            }
        }
        return -1;
    }

    public void eliminarContratosPorVehiculo(int idVehiculo) {
    List<Contrato> contratos = contratoRepo.findByVehiculo_IdVehiculo(idVehiculo);
    contratoRepo.deleteAll(contratos);
}

    //Public obtner contratos por fecha de inicio y fecha de fin
    public List<Contrato> obtenerContratosPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return contratoRepo.findByFechaInicioGreaterThanEqualAndFechaFinLessThanEqual(fechaInicio, fechaFin);
    }
    

}