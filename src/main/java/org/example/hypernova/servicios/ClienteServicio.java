package org.example.hypernova.servicios;

import org.example.hypernova.Extras.EnviarMensaje;
import org.example.hypernova.persistencia.entidades.Cliente;
import org.example.hypernova.persistencia.entidades.Contrato;
import org.example.hypernova.persistencia.entidades.Direccion;
import org.example.hypernova.persistencia.repositorios.ClienteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteServicio implements ClienteService {
    @Autowired
    private ClienteRepo clienteRepo;
    private final DireccionService direccionService;
    @Autowired
    private ContratoServicio contratoServicio;

    public ClienteServicio(DireccionService direccionService) {
        this.direccionService = direccionService;
    }

    @Override
    public Cliente buscarPorRFC(String rfc){
       return clienteRepo.findByRfc(rfc).orElse(null);
    }

    @Override
    public Cliente agregarCliente(Cliente cliente){
        if (cliente.getDireccion() != null) {
            Direccion direccion = direccionService.agregarDireccion(cliente.getDireccion());
            cliente.setDireccion(direccion);
        }
        return clienteRepo.save(cliente);

    }

    @Override
    public Cliente buscarPorId(int idCliente) {
        return clienteRepo.findById(idCliente).orElse(null);
    }

    @Override
    public Cliente actualizarClienteRegistrado(Cliente clienteNuevoDatos){
       Cliente cliente = buscarPorRFC(clienteNuevoDatos.getRfc());
       cliente.setEmail(clienteNuevoDatos.getEmail());
       cliente.setCelular(clienteNuevoDatos.getCelular());
       //Actualizar la direccion
      Direccion direccionActual = cliente.getDireccion();
      Direccion direccionNueva = clienteNuevoDatos.getDireccion();
      direccionNueva.setIdDireccion(direccionActual.getIdDireccion());
      Direccion actualizada = direccionService.actualizarDireccion(direccionNueva);
      cliente.setDireccion(actualizada);
      return clienteRepo.save(cliente);
    }
    @Override
    public String obtenerFolioContrato(String rfc) {
        Cliente cliente = buscarPorRFC(rfc);
        Contrato contrato = contratoServicio
                .obtenerUltimoContratoActivo(cliente.getIdCliente());
        EnviarMensaje mensaje = new EnviarMensaje();
        mensaje.enviarCorreo(cliente.getEmail(),"Recuperacion de folio", contrato.getFolio());
        return contrato.getFolio();
    }




}