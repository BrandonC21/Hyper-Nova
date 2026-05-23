package org.example.hypernova.controladores;

import org.example.hypernova.persistencia.entidades.Cliente;
import org.example.hypernova.servicios.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class controladorClientes {
    @Autowired
    private ClienteService clienteServicio;
    //Agregar Cliente
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente){
        try {
            return ResponseEntity.ok(clienteServicio.agregarCliente(cliente));
        }catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
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
