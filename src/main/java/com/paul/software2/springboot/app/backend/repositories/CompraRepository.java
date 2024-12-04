package com.paul.software2.springboot.app.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.paul.software2.springboot.app.backend.entities.Compra;

public interface CompraRepository extends CrudRepository<Compra, Long>{
    Page<Compra> findAll(Pageable pageable);
}
