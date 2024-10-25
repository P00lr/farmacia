package com.paul.software2.springboot.proyecto.farmacia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paul.software2.springboot.proyecto.farmacia.entities.SubCategoria;
import com.paul.software2.springboot.proyecto.farmacia.repositories.SubCategoriaRepository;

@Service
public class SubCategoriaServiceImp implements SubCategoriaService{

    @Autowired
    private SubCategoriaRepository repository;

    @Override
    public List<SubCategoria> findAll() {
        return (List<SubCategoria>) repository.findAll();
    }

    @Override
    public Optional<SubCategoria> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public SubCategoria save(SubCategoria subCategoria) {
        return repository.save(subCategoria);
    }

    @Override
    public Optional<SubCategoria> delete(SubCategoria subCategoria) {
         Optional<SubCategoria> sOptional = repository.findById(subCategoria.getId());
        sOptional.ifPresent(subDB-> {
            repository.delete(subDB);
        });
        return sOptional;
    }
    
}
