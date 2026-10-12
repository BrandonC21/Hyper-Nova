package org.example.hypernova.controller;

import java.time.LocalDate;
import java.util.List;

import org.example.hypernova.dto.FinalizarContrato;
import org.example.hypernova.model.Contrato;
import org.example.hypernova.model.Vehiculo;
import org.example.hypernova.service.ContratoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {
    private final ContratoServiceImpl contratoServicio;
    public ContratoController(ContratoServiceImpl contratoServicio) {
        this.contratoServicio = contratoServicio;
    }
    //agregar contrato
    @PostMapping
    public ResponseEntity<Contrato> crearContrato(@RequestBody Contrato contrato){
        Contrato nuevoContrato = contratoServicio.crearContrato(contrato);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoContrato);
    }

    //Obtener contrato por id
    @GetMapping("/{id}")
    public ResponseEntity<Contrato> obtenerContrato(@PathVariable int id) {
       Contrato contrato = contratoServicio.buscarPorId(id);
       return ResponseEntity.ok(contrato);
    }
    //Obtner contrato por folio
    @GetMapping("/folio/{folio}")
    public ResponseEntity<Contrato> obtenerContratoPorFolio(@PathVariable String folio) {
       Contrato contrato = contratoServicio.obtnerContrato(folio);
       return ResponseEntity.ok(contrato);
    }

    //Obtener id para finalizar contrato
    @GetMapping("/vehiculo/{idVehiculo}/activo")
    public ResponseEntity<?> obtenerIdContrato(@PathVariable int idVehiculo) {
        try {
            int idContratoActivo = contratoServicio.mostrarIdContrato(idVehiculo);
            if (idContratoActivo != -1) {
                return ResponseEntity.ok(idContratoActivo); 
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay contrato en curso para este vehículo");
            }
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //Obetener los vehiculos disponibles por fecha de inicio y fecha de fin
    @GetMapping("/disponibles/{fechaInicio}/{fechaFin}")
    public ResponseEntity<?> obtenerVehiculosDisponiblesPorFechas(@PathVariable LocalDate fechaInicio, @PathVariable LocalDate fechaFin) {
        List<Vehiculo> vehiculosDisponibles = contratoServicio.obtenerVehiculosDisponiblesPorFechas(fechaInicio, fechaFin);
        if(vehiculosDisponibles != null && !vehiculosDisponibles.isEmpty()) {
            return ResponseEntity.ok(vehiculosDisponibles);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarContrato(@PathVariable int id, @RequestBody FinalizarContrato contrato) {
        try {
            Contrato contratoActualizado = contratoServicio.finalizarContrato(
                id, 
                contrato.getObservaciones(), 
                contrato.getMontoExtra(), 
                contrato.getDescripcionExtra()
            );
            return ResponseEntity.ok(contratoActualizado);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error al finalizar: " + ex.getMessage());
        }
    }

    @PutMapping("{id}/actualizar")
    public ResponseEntity<?> entregarVehiculo(@PathVariable int id){
        try {
            contratoServicio.entrega(id);
            return ResponseEntity.ok("Actualizacion correcta");
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al entregar el vehículo.");
        }
    }
    @PutMapping("/cancelar/{idContrato}")
    public ResponseEntity<?> cancelarContrato(@PathVariable int idContrato) {
        try {
            Contrato contratoCancelado = contratoServicio.cancelarContrato(idContrato);
            return ResponseEntity.ok(contratoCancelado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
}
