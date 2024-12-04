package com.paul.software2.springboot.app.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.paul.software2.springboot.app.backend.entities.Laboratorio;

public interface LaboratorioRepository extends CrudRepository<Laboratorio, Long>{
    Page<Laboratorio> findAll(Pageable pageable);
    boolean existsByNombre(String nombre);
}
