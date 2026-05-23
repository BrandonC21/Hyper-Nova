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
public class ContratoServicio implements ContratoService {

    @Autowired
    private ContratoRepo contratoRepo;

    @Autowired
    private VehiculoServicio vehiculoServicio;

    private final SeguroService seguroServicio;
    private final MetodoService metodoServicio;
    private final ClienteService clienteServicio;
    public ContratoServicio(ClienteService clienteServicio,
                            MetodoService metodoServicio,
                            SeguroService seguroServicio) {
        this.clienteServicio = clienteServicio;
        this.metodoServicio = metodoServicio;
        this.seguroServicio = seguroServicio;


    }

    // Crear el contrato e iniciarlo (Reservado)
    @Override
    public Contrato crearContrato(Contrato contrato){
        try{
            //Guardar el estado del contrato como reservado
            contrato.setEstadoContrato(EstadoContrato.RESERVADO);
            //Vicular
            //Guardar los datos de seguro
            if (contrato.getSeguro() != null) {
                Seguro seguroGuardado = seguroServicio.agregarSeguro(contrato.getSeguro());
                contrato.setSeguro(seguroGuardado);

            }
            //Guardar al cliente si es nuevo
            if(clienteServicio.buscarPorId(contrato.getCliente().getIdCliente())!=null){
                Cliente existente = clienteServicio.buscarPorId(contrato.getCliente().getIdCliente());
                contrato.setCliente(existente);
            }else {
                Cliente cliente = clienteServicio.agregarCliente(contrato.getCliente());
                contrato.setCliente(cliente);
            }
            //Guardar los datos del método de pago
            if(contrato.getMetodoPago() != null){
                MetodoPago metodoGuardado = metodoServicio.agregarMetodo(contrato.getMetodoPago());
                contrato.setMetodoPago(metodoGuardado);
            }

            Vehiculo vehiculo = vehiculoServicio.buscarPorIdV(contrato.getVehiculo().getIdVehiculo());
            long diasRenta = ChronoUnit.DAYS.between(contrato.getFechaInicio(), contrato.getFechaFin());
            contrato.setDeposito(calcularDeposito(vehiculo, diasRenta));
            contrato.setCostoDia(vehiculo.getCostoDia());

            //Vincular el id del empleado que regikstro el automovi
            contrato.setEmpleado(vehiculo.getEmpleado());

            // Marcar el vehiculo como apartado, hasta que se entrege el vehiculo se marcara como rentado
            //vehiculoServicio.marcarRentado(vehiculo.getIdVehiculo());
            vehiculoServicio.marcarApartado(vehiculo.getIdVehiculo());
            return contratoRepo.save(contrato);
        }catch(Exception e){
            throw new RuntimeException("Error creando contrato: " + e.getMessage());
        }

    }

    @Override
    public double calcularDeposito(Vehiculo vehiculo, long diasRenta) {
        // El depósito es el 20% por anticipado que son 5 dias
        double costoEstimado = vehiculo.getCostoDia() * diasRenta; // Asumiendo una renta de 5 días para el cálculo
        return costoEstimado * 0.20; // 20% del costo estimado

    }
    @Override
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

    @Override
    // Cancelar un contrato en caso de que el cliente ya no requiera el servicio
    public Contrato cancelarContrato(int idContrato) throws Exception {
        try {
            // Buscar el contrato por su ID
            List<Contrato> contratos = contratoRepo.findByIdContrato(idContrato);

            if (!contratos.isEmpty()) {
                Contrato contrato = contratos.get(0);
                contrato.setEstadoContrato(EstadoContrato.CANCELADO);
                if (contrato.getVehiculo() != null) {
                    vehiculoServicio.marcarDisponible(contrato.getVehiculo().getIdVehiculo());
                }
                return contratoRepo.save(contrato);
            } else {
                throw new Exception("No se encontró el contrato con ID: " + idContrato);
            }
        } catch (Exception e) {
            throw new Exception("Error al cancelar el contrato: " + e.getMessage());
        }
    }

    @Override
    public Contrato buscarPorId(int idContrato) {
        return contratoRepo.findById(idContrato).get();
    }

    @Override
    public List<Contrato> obtenerContratosPorVehiculo(int idVehiculo) {
        return contratoRepo.findByVehiculo_IdVehiculo(idVehiculo);
    }

    @Override
    //Obtner el id del contrato para poder finalizrlo
    public int mostrarIdContrato(int idVehiculo) throws Exception {
        List<Contrato> contratos = obtenerContratosPorVehiculo(idVehiculo);
        for (Contrato contrato : contratos) {
            if (contrato.getEstadoContrato() == EstadoContrato.EN_CURSO) {
                return contrato.getIdContrato();
            }
        }
        throw new Exception("No se encontro un contrato en curso para el vehiculo con ID: " + idVehiculo);
    }
    @Override
    public void eliminarContratosPorVehiculo(int idVehiculo) {
        List<Contrato> contratos = contratoRepo.findByVehiculo_IdVehiculo(idVehiculo);
        contratoRepo.deleteAll(contratos);
    }
    @Override
    //Public obtner contratos por fecha de inicio y fecha de fin
    public List<Contrato> obtenerContratosPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return contratoRepo.findByFechaInicioGreaterThanEqualAndFechaFinLessThanEqual(fechaInicio, fechaFin);
    }
    @Override
    //Aprartir del contato obtner los autos disponibles por fecha de inicio y fecha de fin
    public List<Vehiculo> obtenerVehiculosDisponiblesPorFechas(LocalDate fechaInicio, LocalDate fechaFin){
        //Obtner todos los vehiculos
        List<Vehiculo> vehiculos  = vehiculoServicio.obtenerVehiculos();
        List<Contrato> contratos = obtenerContratosPorFechas(fechaInicio, fechaFin);
        List<Vehiculo> vehiculosOcupados = new ArrayList<>();
        for (Contrato contrato : contratos){
            if(contrato.getVehiculo() != null){
                vehiculosOcupados.add(contrato.getVehiculo());
            }
        }
        vehiculos.removeAll(vehiculosOcupados);
        return vehiculos;
    }

}