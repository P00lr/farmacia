package com.paul.software2.springboot.app.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.paul.software2.springboot.app.backend.entities.DetalleVenta;
import com.paul.software2.springboot.app.backend.entities.DetalleVentaId;

public interface DetalleVentaRepository extends CrudRepository<DetalleVenta, DetalleVentaId> {

}
