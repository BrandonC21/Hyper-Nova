package org.example.hypernova.controladores;

import java.time.LocalDate;
import java.util.List;

import org.example.hypernova.dto.FinalizarContrato;
import org.example.hypernova.persistencia.entidades.Contrato;
import org.example.hypernova.persistencia.entidades.Vehiculo;
import org.example.hypernova.servicios.ContratoServicio;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ControladorContratos {
    @Autowired
    private ContratoServicio contratoServicio;

    //agregar contrato
    @PostMapping
    public ResponseEntity<Contrato> crearContrato(@RequestBody Contrato contrato){
        try {
            return ResponseEntity.ok(contratoServicio.crearContrato(contrato));
        }catch (Exception ex){
            System.err.println("Error al crear contrato: " + ex.getMessage());
            ex.printStackTrace(); 
            return ResponseEntity.badRequest().build();
        }
    }

    //Obtener contrato por id
    @GetMapping("/{id}")
    public ResponseEntity<Contrato> obtenerContrato(@PathVariable int id) {
        try {
            Contrato contrato = contratoServicio.buscarPorId(id); 
            if(contrato != null) {
                return ResponseEntity.ok(contrato);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
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
    
}
