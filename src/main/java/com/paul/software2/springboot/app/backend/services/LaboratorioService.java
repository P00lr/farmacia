package com.paul.software2.springboot.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paul.software2.springboot.app.backend.entities.Laboratorio;

public interface LaboratorioService {
    List<Laboratorio> listarTodo();
    Page<Laboratorio> paginarTodo(Pageable pageable);
    Optional<Laboratorio> buscarPorId(Long id);
    Laboratorio guardar(Laboratorio laboratorio);
    Optional<Laboratorio> actualizar(Long id, Laboratorio la);
    Optional<Laboratorio> eliminar(Long id);
    boolean existeElLaboratorio(String laboratorioNombre);
}
