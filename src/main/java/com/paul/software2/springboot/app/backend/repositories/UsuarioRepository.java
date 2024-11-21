package com.paul.software2.springboot.app.backend.repositories;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.paul.software2.springboot.app.backend.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
    boolean existsByUsername(String username);
    Optional<Usuario> findByUsername(String username);
    Page<Usuario> findAll(Pageable pageable);
}
