package org.example.hypernova.controladores;

import jakarta.servlet.http.HttpSession;
import org.example.hypernova.persistencia.entidades.Empleado;
import org.example.hypernova.persistencia.entidades.Vehiculo;
import org.example.hypernova.servicios.VehiculoService;
import org.example.hypernova.servicios.VehiculoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehiculos")
public class ControllerVehiculo {

    @Autowired
    private VehiculoService vehiculoServicio;

    //Obtener todos los vehiculos
    @GetMapping
    public ResponseEntity<List<Vehiculo>> obtenerVehiculos(){
        return ResponseEntity.ok(vehiculoServicio.obtenerVehiculos());
    }

    //Obtener todos los vehiculos disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Vehiculo>> obtenerVehiculosDisponibles(){
        return ResponseEntity.ok(vehiculoServicio.obtnerList());
    }

    //Buscar vehiculos por marca o medelo
   @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Vehiculo>> busquedaVehiculos(@PathVariable String nombre){
        //return ResponseEntity.ok(vehiculoServicio.buscarVehiculos(nombre));
        List <Vehiculo> vehiculos = vehiculoServicio.buscarVehiculos(nombre);
        if(vehiculos != null && !vehiculos.isEmpty()){
            return ResponseEntity.ok(vehiculos);
        }else{
            return ResponseEntity.notFound().build();
        }
     
    }
    //Registar Vehiculo
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registrarVehiculo(
            @RequestPart("vehiculo") Vehiculo vehiculo,
            @RequestPart("imagen") MultipartFile archivoImagen,
            HttpSession session) {
        try {
            Empleado empleado = (Empleado) session.getAttribute("UsuarioLogueado");
            vehiculo.setEmpleado(empleado);
            if (vehiculo.getNumSerie() == null || vehiculo.getNumSerie().isEmpty()) {
                return ResponseEntity.badRequest().body("El numero de serie es obligatorio");
            }
            if (archivoImagen.isEmpty()) {
                return ResponseEntity.badRequest().body("La imagen es obligatoria");
            }
            //Ruta de la carpeta
            String carpeta = System.getProperty("user.dir")
                    + "/src/main/resources/static/imagenes/";
            File directorio = new File(carpeta);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            String nomImagen = System.currentTimeMillis() + "_" + archivoImagen.getOriginalFilename();
            Path ruta = Paths.get(carpeta + nomImagen);
            Files.write(ruta, archivoImagen.getBytes());
            vehiculo.setUrlImagen("/imagenes/" + nomImagen);
            Vehiculo guardado = vehiculoServicio.agregarVehiculo(vehiculo);
            return ResponseEntity.ok(guardado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar: " + e.getMessage());
        }
    }


    //Obtener feche de contrato de vehiculo
    @GetMapping("/{id}/fecha")
    public ResponseEntity<String> obtenerFechaContrato(@PathVariable int id) {
        try {
            String fecha = vehiculoServicio.obtenerFecha(id);
            return ResponseEntity.ok(fecha);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


   // Eliminar vehiculo
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVehiculo(@PathVariable int id) {
        try {
            vehiculoServicio.borrarVehiculo(id); // Le pasamos solo el ID
            return ResponseEntity.ok("Se eliminó correctamente");
        } catch (Exception e) {
            // Mandamos el mensaje de error del servicio al frontend
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/numserie/{numSerie}")
    public ResponseEntity<Vehiculo> buscarPorNumSerie(@PathVariable String numSerie) {
        Vehiculo vehiculo = vehiculoServicio.buscarVehiculo(numSerie);

        if (vehiculo != null) {
            return ResponseEntity.ok(vehiculo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Detalle del vehiculo
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> buscarVehiculoPorId(@PathVariable int id) {
        Vehiculo vehiculo = vehiculoServicio.buscarPorIdV(id);
        if (vehiculo != null) {
            return ResponseEntity.ok(vehiculo);
        }else  {
            return ResponseEntity.notFound().build();
        }
    }


    // Marcar vehículo como RENTADO
    @PutMapping("/{id}/rentar")
    public ResponseEntity<String> marcarComoRentado(@PathVariable int id) {
        try {
            vehiculoServicio.marcarRentado(id);
            return ResponseEntity.ok("El vehículo ha sido marcado como RENTADO.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cambiar el estado a rentado.");
        }
    }

    // Marcar vehículo como en MANTENIMIENTO
    @PutMapping("/{id}/mantenimiento")
    public ResponseEntity<String> marcarComoMantenimiento(@PathVariable int id) {
        try {
            vehiculoServicio.marcarMantenimiento(id);
            return ResponseEntity.ok("El vehículo ha sido enviado a MANTENIMIENTO.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al mandar el vehículo a mantenimiento.");
        }
    }

    // Marcar vehículo como DISPONIBLE
    @PutMapping("/{id}/disponible")
    public ResponseEntity<String> marcarComoDisponible(@PathVariable int id) {
        try {
            vehiculoServicio.marcarDisponible(id);
            return ResponseEntity.ok("El vehículo ahora está DISPONIBLE.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al hacer disponible el vehículo.");
        }
    }

}