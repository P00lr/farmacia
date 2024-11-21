package com.paul.software2.springboot.app.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.paul.software2.springboot.app.backend.entities.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{
    Page<Cliente> findAll(Pageable pageable);
}
