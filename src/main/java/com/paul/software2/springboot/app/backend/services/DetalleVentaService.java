package com.paul.software2.springboot.app.backend.services;

import java.util.Optional;

import com.paul.software2.springboot.app.backend.entities.DetalleVenta;
import com.paul.software2.springboot.app.backend.entities.DetalleVentaId;

public interface DetalleVentaService {
    Iterable<DetalleVenta> listarTodos();
    Optional<DetalleVenta> buscarPorId(DetalleVentaId id);
    DetalleVenta guardar(DetalleVenta detalleVenta);
    void eliminarPorId(DetalleVentaId  id);
}
