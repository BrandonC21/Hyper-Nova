package org.example.hypernova.service;
import org.example.hypernova.model.Cliente;


public interface ClienteService {
    Cliente buscarPorRFC(String rfc);
    Cliente agregarCliente(Cliente cliente);
    Cliente buscarPorId(int idCliente);
    Cliente actualizarClienteRegistrado(Cliente clienteNuevoDatos);
    String obtenerFolioContrato(String rfc);

}
