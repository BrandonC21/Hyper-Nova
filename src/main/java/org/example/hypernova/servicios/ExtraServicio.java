package org.example.hypernova.servicios;

import org.example.hypernova.persistencia.entidades.Extra;
import org.example.hypernova.persistencia.repositorios.ExtraRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtraServicio implements ExtraService {
    @Autowired
    private ExtraRepo extraRepo;
    @Override
    public Extra agregarExtra(Extra extra) throws Exception {
        try {
            return extraRepo.save(extra);
        }catch (Exception e) {
            throw new Exception("Error al agregar el extra");
        }
    }

}
