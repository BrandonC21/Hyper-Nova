package org.example.hypernova.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistasController {

    // 1. Mostrar el Login
    @GetMapping("/login")
    public String mostrarLogin() {
        // Devuelve exactamente el nombre de tu archivo sin el ".html"
        return "login-admin";
    }

    // 2. Mostrar el Panel de Administrador
    @GetMapping("/admin/panel")
    public String mostrarPanelAdmin() {
        return "panel-admin";
    }

    // 3. Mostrar el Registro de Automóviles
    @GetMapping("/empleados/registrar-auto")
    public String mostrarRegistroAuto() {
        // Ojo aquí: Tu archivo se llama "registar-automovil" (le falta una 'r' en registrar).
        // Spring Boot buscará el nombre exacto que le digas aquí.
        return "registar-automovil";
    }

    // 4. Mostrar el Catálogo
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