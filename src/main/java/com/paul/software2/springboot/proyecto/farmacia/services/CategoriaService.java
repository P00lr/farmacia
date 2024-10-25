package com.paul.software2.springboot.proyecto.farmacia.services;

import java.util.List;

import java.util.Optional;

import com.paul.software2.springboot.proyecto.farmacia.entities.Categoria;

public interface CategoriaService {
    List<Categoria> findAll();
    Optional<Categoria> findById(Long id);
    Categoria save(Categoria categoria);
    Optional<Categoria> delete(Categoria categoria); 
}
