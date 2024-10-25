package com.paul.software2.springboot.proyecto.farmacia.services;

import java.util.Optional;
import java.util.List;

import com.paul.software2.springboot.proyecto.farmacia.entities.SubCategoria;

public interface SubCategoriaService{
    List<SubCategoria> findAll();
    Optional<SubCategoria> findById(Long id);
    SubCategoria save(SubCategoria subCategoria);
    Optional<SubCategoria> delete(SubCategoria subCategoria);
}
