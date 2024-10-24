package com.paul.software2.springboot.proyecto.farmacia.services;

import java.util.List;
import java.util.Optional;

import com.paul.software2.springboot.proyecto.farmacia.entities.Laboratorio;

public interface LaboratorioService {
    List<Laboratorio> findAll();
    Optional<Laboratorio> findById(Long id);
    Laboratorio save(Laboratorio laboratorio);
    Optional<Laboratorio> delete(Laboratorio laboratorio);
}
