package com.paul.software2.springboot.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paul.software2.springboot.app.backend.entities.Compra;

public interface CompraService {
    List<Compra> listarTodo();
    Page<Compra> paginarTodo(Pageable pageable);
    Optional<Compra> buscarPorId(Long id);
    Compra guardar(Compra compra);
    Compra crearCompraConDetalle(Compra compra);
    //Compra crearCompraConDetalle(Compra compra);
    Optional<Compra> eliminar(Long id);
}
