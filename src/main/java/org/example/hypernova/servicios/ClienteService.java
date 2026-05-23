package org.example.hypernova.servicios;

import org.example.hypernova.persistencia.entidades.Cliente;

import java.util.Optional;

public interface ClienteService {
    Cliente buscarPorRFC(String rfc);
    Cliente agregarCliente(Cliente cliente);
    Cliente buscarPorId(int idCliente);
    Cliente actualizarClienteRegistrado(Cliente clienteNuevoDatos);

}
