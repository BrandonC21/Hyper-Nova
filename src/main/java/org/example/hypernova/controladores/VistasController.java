package org.example.hypernova.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistasController {
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login-admin";
    }

    
    @GetMapping("/admin/panel")
    public String mostrarPanelAdmin() {
        return "panel-admin";
    }

   
    @GetMapping("/empleados/registrar-auto")
    public String mostrarRegistroAuto() {
        return "registar-automovil";
    }

    @GetMapping("/catalogo")
    public String mostrarCatalogo() {
        return "catalogo";
    }

    @GetMapping("/detalle-vehiculo.html")
    public String mostrarDetalle() {
        return "detalle-vehiculo";
    }
    @GetMapping("/registrar-cliente.html")
    public String mostrarRegistroCliente() {
        return "registrar-cliente";
    }

    @GetMapping("/nuevo-contrato.html")
    public String mostrarNuevoContrato() {
        return "nuevo-contrato";
    }
    @GetMapping("/visualizar-contrato.html")
    public String mostrarVisualizarContrato() {
        return "visualizar-contrato";
    }
}