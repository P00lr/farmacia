package com.paul.software2.springboot.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paul.software2.springboot.app.backend.entities.Medicamento;

public interface MedicamentoService {
    List<Medicamento> listarTodo();
    Page<Medicamento> paginarTodo(Pageable pageable);
    Optional<Medicamento> buscarPorId(Long id);
    Medicamento guardar(Medicamento cliente);
    Optional<Medicamento> actualizar(Long id, Medicamento cliente);
    Optional<Medicamento> eliminar(Long id);
}
