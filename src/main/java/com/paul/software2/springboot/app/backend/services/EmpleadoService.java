package com.paul.software2.springboot.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paul.software2.springboot.app.backend.entities.Empleado;

public interface EmpleadoService {
    List<Empleado> listarTodo();
    Page<Empleado> paginarTodo(Pageable pageable);
    Optional<Empleado> buscarPorId(Long id);
    Empleado guardar(Empleado empleado);
    Optional<Empleado> actualizar(Long id, Empleado empleado);
    Optional<Empleado> eliminar(Long id);
}
