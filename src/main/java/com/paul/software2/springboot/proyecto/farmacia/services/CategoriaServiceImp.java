package com.paul.software2.springboot.proyecto.farmacia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paul.software2.springboot.proyecto.farmacia.entities.Categoria;
import com.paul.software2.springboot.proyecto.farmacia.repositories.CategoriaRepository;

@Service
public class CategoriaServiceImp implements CategoriaService{

    @Autowired
    private CategoriaRepository repository;

    @Override
    public List<Categoria> findAll() {
        return (List<Categoria>) repository.findAll();
    }

    @Override
    public Optional<Categoria> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    @Override
    public Optional<Categoria> delete(Categoria categoria) {
        Optional<Categoria> cOptional = repository.findById(categoria.getId());
        cOptional.ifPresent(catDB-> {
            repository.delete(catDB);
        });
        return cOptional;
    }
    
}
