package com.paul.software2.springboot.proyecto.farmacia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.software2.springboot.proyecto.farmacia.entities.Laboratorio;
import com.paul.software2.springboot.proyecto.farmacia.repositories.LaboratorioRepository;

@Service
public class LaboratorioServiceImp implements LaboratorioService{

    @Autowired
    private LaboratorioRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Laboratorio> findAll() {
         return (List<Laboratorio>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Laboratorio> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Laboratorio save(Laboratorio laboratorio) {
        return repository.save(laboratorio);
        
    }

    @Override
    public Optional<Laboratorio> delete(Laboratorio laboratorio) {
        Optional<Laboratorio> labOptional = repository.findById(laboratorio.getId());
        labOptional.ifPresent(labDb->{
            repository.delete(labDb);
        });
        return labOptional;
    }
    
}
