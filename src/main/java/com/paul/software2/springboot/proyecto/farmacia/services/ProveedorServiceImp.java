package com.paul.software2.springboot.proyecto.farmacia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.software2.springboot.proyecto.farmacia.entities.Proveedor;
import com.paul.software2.springboot.proyecto.farmacia.repositories.ProveedorRepository;

@Service
public class ProveedorServiceImp implements ProveedorService{
   
    @Autowired
    private ProveedorRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> findAll() {
         return (List<Proveedor>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Proveedor> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Proveedor save(Proveedor proveedor) {
        return repository.save(proveedor);
        
    }

    @Override
    public Optional<Proveedor> delete(Proveedor proveedor) {
        Optional<Proveedor> pOptional = repository.findById(proveedor.getId());
        pOptional.ifPresent(pDb->{
            repository.delete(pDb);
        });
        return pOptional;
    }
}
