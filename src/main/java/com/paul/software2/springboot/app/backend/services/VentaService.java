package com.paul.software2.springboot.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paul.software2.springboot.app.backend.entities.Venta;

public interface VentaService {
    List<Venta> listarTodo();
    Page<Venta> paginarTodo(Pageable pageable);
    Optional<Venta> buscarPorId(Long id);
    Venta guardar(Venta venta);
    Venta crearVentaConDetalle(Venta venta);
    Optional<Venta> eliminar(Long id);
}
