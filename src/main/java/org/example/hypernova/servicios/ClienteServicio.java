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

    // INYECTAMOS el repositorio de la dirección para poder guardar los cambios allí también
    @Autowired
    private DireccionRepo direccionRepo;

    //  Agregar Cliente
    public Cliente agregarCliente(Cliente cliente) {
        try {
            // Si el cliente trae una dirección nueva, primero debemos guardarla en su tabla
            if (cliente.getDireccion() != null) {
                Direccion dirGuardada = direccionRepo.save(cliente.getDireccion());
                cliente.setDireccion(dirGuardada);
            }
            return clienteRepo.save(cliente);
        } catch (Exception e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
            return null;
        }
    }
    //Buscar cliente por rfc
    public Cliente buscarporRFC(String rfc) {
       return clienteRepo.findByrfc(rfc).orElse(null);
    }

    //Buscar por id
    public Cliente buscarPorId(int idCliente) {
        return clienteRepo.findById(idCliente).get();
    }

    // Si el cliente fue registrado, solo actualizar los campos necesarios
    public Cliente actualizarClienteRegistrado(Cliente clienteNuevosDatos) {
        try {
            String rfc = clienteNuevosDatos.getRfc();
            Optional<Cliente> clienteOpt = clienteRepo.findByrfc(rfc);

            if (clienteOpt.isPresent()) {
                Cliente existente = clienteOpt.get();

                // Actualizar campos propios del cliente
                existente.setEmail(clienteNuevosDatos.getEmail());
                existente.setCelular(clienteNuevosDatos.getCelular());

                // --- ACTUALIZAR LA DIRECCIÓN (La tabla externa) ---
                if (clienteNuevosDatos.getDireccion() != null) {
                    Direccion direccionExistente = existente.getDireccion();
                    Direccion direccionNuevosDatos = clienteNuevosDatos.getDireccion();

                    if (direccionExistente != null) {
                        // Si ya tenía dirección, actualizamos sus campos (ajusta los getters según tu clase Direccion)
                        direccionExistente.setCalle(direccionNuevosDatos.getCalle());
                        direccionExistente.setColonia(direccionNuevosDatos.getColonia());
                        direccionExistente.setDelegacion(direccionNuevosDatos.getDelegacion());
                        direccionExistente.setCodigoPostal(direccionNuevosDatos.getCodigoPostal());

                        // Guardamos los cambios de la dirección en su respectiva tabla
                        direccionRepo.save(direccionExistente);
                    } else {
                        // Si por alguna razón el cliente viejo no tenía dirección, se la creamos nueva
                        Direccion nuevaDirGuardada = direccionRepo.save(direccionNuevosDatos);
                        existente.setDireccion(nuevaDirGuardada);
                    }
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