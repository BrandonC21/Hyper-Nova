package org.example.hypernova.servicios;

import org.example.hypernova.persistencia.entidades.Cliente;
import org.example.hypernova.persistencia.entidades.Contrato;

import java.util.Optional;

public interface ClienteService {
    Cliente buscarPorRFC(String rfc);
    Cliente agregarCliente(Cliente cliente);
    Cliente buscarPorId(int idCliente);
    Cliente actualizarClienteRegistrado(Cliente clienteNuevoDatos);
    String obtenerFolioContrato(String rfc);

}
