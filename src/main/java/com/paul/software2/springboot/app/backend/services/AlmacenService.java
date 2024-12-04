package com.paul.software2.springboot.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paul.software2.springboot.app.backend.entities.Almacen;
import com.paul.software2.springboot.app.backend.entities.DetalleAlmacen;

public interface AlmacenService {
    List<Almacen> listarTodo();
    Page<Almacen> paginarTodo(Pageable pageable);
    Optional<Almacen> buscarPorId(Long id);
    Almacen guardar(Almacen almacen);
    Optional<Almacen> actualizar(Long id, Almacen almacen);
    Optional<Almacen> eliminar(Long id);
    
    void agregarMedicamento(DetalleAlmacen detalleAlmacen);
    boolean existeElAlmacen(String almacenNombre);
}
