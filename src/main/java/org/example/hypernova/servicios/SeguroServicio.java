package org.example.hypernova.servicios;


import org.example.hypernova.enmus.TipoSeguro;
import org.example.hypernova.persistencia.entidades.Seguro;
import org.example.hypernova.persistencia.repositorios.SeguroRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeguroServicio {
    @Autowired
    private SeguroRepo seguroRepo;

    //Agregar Seguro del vehiculo
    public Seguro agregarSeguro(Seguro seguro){
        seguro.setCosto(calcularCosto(seguro));
        return seguroRepo.save(seguro);
    }

    //Calcular el costo del seguro de acuerdo al tipo
    public double calcularCosto(Seguro seguro) {
        if(seguro.getTipoSeguro() == TipoSeguro.COBERTURA_TERCEROS) {
            return 129.35;
        } else if (seguro.getTipoSeguro() == TipoSeguro.COBERTURA_PREMIUM) {
            return 385.65;
        } else if (seguro.getTipoSeguro() == TipoSeguro.COBERTURA_LIMITADA) {
            return 249.65;
        } else if (seguro.getTipoSeguro() == TipoSeguro.COBERTURA_AMPLIA) {
            return 189.99;
        }
        return 0.0;
    }
    
    //Actualizar el seguro
    public Seguro modificarSeguro(int id, Seguro seguroActualizado) {
        return seguroRepo.findById(id).map(seguroExistente -> {
            // 1. Actualizamos el nombre de la aseguradora
            seguroExistente.setNombreAseguradora(seguroActualizado.getNombreAseguradora());
            // 2. Actualizamos el tipo de seguro
            seguroExistente.setTipoSeguro(seguroActualizado.getTipoSeguro());
            // 3. ¡IMPORTANTE! Recalculamos el costo basado en el nuevo tipo
            seguroExistente.setCosto(calcularCosto(seguroExistente));
            // 4. Guardamos los cambios
            return seguroRepo.save(seguroExistente);
        }).orElseThrow(() -> new RuntimeException("Seguro no encontrado con ID: " + id));
    }

}
