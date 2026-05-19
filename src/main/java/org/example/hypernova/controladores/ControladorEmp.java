package org.example.hypernova.controladores;

import jakarta.servlet.http.HttpSession;
import org.example.hypernova.persistencia.entidades.Empleado;
import org.example.hypernova.servicios.EmpleadoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/empleados")
public class ControladorEmp {
    @Autowired
    private EmpleadoServicio empleadoServicio;
    //Validar Empleado
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> validarLogin(@RequestParam("email") String email, @RequestParam("password") String password,
                                                            HttpSession session) {
        Map<String, Object> respuesta = new HashMap<>();
        Empleado empleadoValido = empleadoServicio.login(email, password);
        if (empleadoValido != null) {
            session.setAttribute("UsuarioLogueado", empleadoValido);
            respuesta.put("exito", true);
            respuesta.put("rol", empleadoValido.getRol().name());
            //  Manda la respuesta como ok si todo es valido
            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.put("exito", false);
            respuesta.put("mensaje", "Contraseña o Correo son incorrecto");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
    }

    //Agregar Empleado
    @PostMapping
    public ResponseEntity<Empleado> registrarEmpleado(@RequestBody Empleado empleado) {
        try {
            return ResponseEntity.ok(empleadoServicio.agregarEmpleado(empleado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
