package org.example.hypernova.service;


import org.example.hypernova.enums.TipoSeguro;
import org.example.hypernova.model.Seguro;
import org.example.hypernova.repository.SeguroRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeguroServiceImp implements SeguroService{
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
    public Seguro modificarSeguro(Seguro seguroActualizado) {
        /*
        return seguroRepo.findById(id).map(seguroExistente -> {
            seguroExistente.setNombreAseguradora(seguroActualizado.getNombreAseguradora());
            seguroExistente.setTipoSeguro(seguroActualizado.getTipoSeguro());
            seguroExistente.setCosto(calcularCosto(seguroExistente));
            return seguroRepo.save(seguroExistente);
        }).orElseThrow(() -> new RuntimeException("Seguro no encontrado con ID: " + id));
        */
       Seguro seguroExistente = buscarPorId(seguroActualizado.getIdSeguro());
        seguroExistente.setNombreAseguradora(seguroActualizado.getNombreAseguradora());
        seguroExistente.setTipoSeguro(seguroActualizado.getTipoSeguro());
        seguroExistente.setCosto(calcularCosto(seguroExistente));
        return seguroRepo.save(seguroExistente);       
    }

    @Override
    public Seguro buscarPorId(int id) {
        return seguroRepo.findById(id).orElseThrow(() -> new RuntimeException("Seguro no encontrado con ID: " + id));
    }


}
