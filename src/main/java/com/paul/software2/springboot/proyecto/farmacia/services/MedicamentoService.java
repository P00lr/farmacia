package com.paul.software2.springboot.proyecto.farmacia.services;

import java.util.List;
import java.util.Optional;

import com.paul.software2.springboot.proyecto.farmacia.entities.Medicamento;

public interface MedicamentoService {
    List<Medicamento> findAll();
    Optional<Medicamento> findById(Long id);
    Medicamento save(Medicamento medicamento);
    Optional<Medicamento> delete(Medicamento medicamento);
}
