package com.paul.software2.springboot.app.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.paul.software2.springboot.app.backend.entities.Venta;

public interface VentaRepository extends CrudRepository<Venta, Long>{
    Page<Venta> findAll(Pageable pageable);
}
