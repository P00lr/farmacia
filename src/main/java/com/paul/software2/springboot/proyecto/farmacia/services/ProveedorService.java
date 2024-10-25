package com.paul.software2.springboot.proyecto.farmacia.services;

import java.util.List;
import java.util.Optional;

import com.paul.software2.springboot.proyecto.farmacia.entities.Proveedor;

public interface ProveedorService {
    List<Proveedor> findAll();
    Optional<Proveedor> findById(Long id);
    Proveedor save(Proveedor proveedor);
    Optional<Proveedor> delete(Proveedor proveedor);
}