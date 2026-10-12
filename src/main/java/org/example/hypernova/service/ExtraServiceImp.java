package org.example.hypernova.service;

import org.example.hypernova.model.Extra;
import org.example.hypernova.repository.ExtraRepo;
import org.springframework.stereotype.Service;

@Service
public class ExtraServiceImp implements ExtraService {
    private final ExtraRepo extraRepo;
    
    public ExtraServiceImp(ExtraRepo extraRepo) {
        this.extraRepo = extraRepo;
    }
    @Override
    public Extra agregarExtra(Extra extra) throws Exception {
        return extraRepo.save(extra);
    }

}
