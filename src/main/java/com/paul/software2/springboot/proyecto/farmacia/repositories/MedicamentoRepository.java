package com.paul.software2.springboot.proyecto.farmacia.repositories;

import org.springframework.data.repository.CrudRepository;

import com.paul.software2.springboot.proyecto.farmacia.entities.Medicamento;

public interface MedicamentoRepository extends CrudRepository<Medicamento, Long> {

}
