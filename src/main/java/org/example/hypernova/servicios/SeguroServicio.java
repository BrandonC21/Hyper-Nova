package org.example.hypernova.servicios;


import org.example.hypernova.enmus.TipoSeguro;
import org.example.hypernova.persistencia.entidades.Seguro;
import org.example.hypernova.persistencia.repositorios.SeguroRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeguroServicio implements SeguroService{
    @Autowired
    private SeguroRepo seguroRepo;

    //Agregar Seguro
    @Override
    public Seguro agregarSeguro(Seguro seguro)throws Exception{
        try {
            seguro.setCosto(calcularCosto(seguro));
            return seguroRepo.save(seguro);
        }catch (Exception e){
            throw new Exception("Error al agregar seguro");
        }
    }

    //Calcular el costo del seguro de acuerdo al tipo
    @Override
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

    @Override
    public Seguro modificarSeguro(int id, Seguro seguroActualizado) {
        return seguroRepo.findById(id).map(seguroExistente -> {
            seguroExistente.setNombreAseguradora(seguroActualizado.getNombreAseguradora());
            seguroExistente.setTipoSeguro(seguroActualizado.getTipoSeguro());
            seguroExistente.setCosto(calcularCosto(seguroExistente));
            return seguroRepo.save(seguroExistente);
        }).orElseThrow(() -> new RuntimeException("Seguro no encontrado con ID: " + id));
    }


}
