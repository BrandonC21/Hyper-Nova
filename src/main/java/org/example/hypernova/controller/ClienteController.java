package org.example.hypernova.controller;

import org.example.hypernova.model.Cliente;
import org.example.hypernova.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteServicio;

    public ClienteController(ClienteService clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    //Agregar Cliente
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente){
        Cliente clienteNuevo = clienteServicio.agregarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteNuevo);
    }

    //Buscar cliente por medio de rfc ya que es unico
    @GetMapping("/{rfc}")
    public ResponseEntity<Cliente> buscarcliente(@PathVariable String rfc) {
        Cliente cliente = clienteServicio.buscarPorRFC(rfc);
        if( cliente != null) {
            return ResponseEntity.ok(cliente);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/obtener/{rfc}")
    public ResponseEntity<?> buscarFolio(@PathVariable String rfc) {
        try {
            String folio = clienteServicio.obtenerFolioContrato(rfc);
            return ResponseEntity.ok(folio);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    //Actualizar cliente
    @PutMapping("/actualizar")
    public ResponseEntity<Cliente> actualizarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente clienteActualizado = clienteServicio.actualizarClienteRegistrado(cliente);
            if(clienteActualizado != null) {
                return ResponseEntity.ok(clienteActualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }


}
