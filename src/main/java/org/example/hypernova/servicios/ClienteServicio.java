package org.example.hypernova.servicios;

import org.example.hypernova.persistencia.entidades.Cliente;
import org.example.hypernova.persistencia.entidades.Direccion;
import org.example.hypernova.persistencia.repositorios.ClienteRepo;
import org.example.hypernova.persistencia.repositorios.DireccionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepo clienteRepo;
    @Autowired
    private DireccionRepo direccionRepo;

    //  Agregar Cliente
    public Cliente agregarCliente(Cliente cliente) {
        /* 
        try {
            if (cliente.getDireccion() != null) {
                Direccion dirGuardada = direccionRepo.save(cliente.getDireccion());
                cliente.setDireccion(dirGuardada);
            }
            return clienteRepo.save(cliente);
        } catch (Exception e) {
            return null;
        }
        */
       if(cliente.getDireccion() != null){
            Direccion dirGuardada = direccionRepo.save(cliente.getDireccion());
            cliente.setDireccion(dirGuardada);
        }
        return clienteRepo.save(cliente);
       
    }
    //Buscar cliente por rfc
    public Cliente buscarporRFC(String rfc) {
       return clienteRepo.findByRfc(rfc).orElse(null);
    }

    //Buscar por id
    public Cliente buscarPorId(int idCliente) {
        return clienteRepo.findById(idCliente).get();
    }

    //Actualizar cliente registrado
    public Cliente actualizarClienteRegistrado(Cliente clienteNuevosDatos) {
        try {
            String rfc = clienteNuevosDatos.getRfc();
            Optional<Cliente> clienteOpt = clienteRepo.findByRfc(rfc);

            if (clienteOpt.isPresent()) {
                Cliente existente = clienteOpt.get();

                // Actualiza los campos de clientes que pueden ser mofificados
                existente.setEmail(clienteNuevosDatos.getEmail());
                existente.setCelular(clienteNuevosDatos.getCelular());

                //Actualizar la direccion del cliente
                if (clienteNuevosDatos.getDireccion() != null) {
                    Direccion direccionExistente = existente.getDireccion();
                    Direccion direccionNuevosDatos = clienteNuevosDatos.getDireccion();
                    //Se actualiza los campos de la direccion
                    direccionExistente.setCalle(direccionNuevosDatos.getCalle());
                    direccionExistente.setColonia(direccionNuevosDatos.getColonia());
                    direccionExistente.setDelegacion(direccionNuevosDatos.getDelegacion());
                    direccionExistente.setCodigoPostal(direccionNuevosDatos.getCodigoPostal());
                    //Guardamo la dirección actualizada
                    direccionRepo.save(direccionExistente);
                   
                }
                System.out.println("Cliente con RFC " + rfc + " actualizado correctamente.");
                return clienteRepo.save(existente);
            } else {
                System.out.println("No se encontró el cliente con RFC: " + rfc);
                return null;
            }

        } catch (Exception e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return null;
        }
    }
}