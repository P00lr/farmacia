package com.paul.software2.springboot.app.backend.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.paul.software2.springboot.app.backend.entities.Rol;

public interface RolRepository extends CrudRepository<Rol, Long>{
    Optional<Rol> findByNombre(String nombre);
}
