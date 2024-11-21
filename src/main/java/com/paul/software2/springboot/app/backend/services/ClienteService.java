package com.paul.software2.springboot.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paul.software2.springboot.app.backend.entities.Cliente;

public interface ClienteService {
    List<Cliente> listarTodo();
    Page<Cliente> paginarTodo(Pageable pageable);
    Optional<Cliente> buscarPorId(Long id);
    Cliente guardar(Cliente cliente);
    Optional<Cliente> actualizar(Long id, Cliente cliente);
    Optional<Cliente> eliminar(Long id);
}
