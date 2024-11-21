package com.paul.software2.springboot.app.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paul.software2.springboot.app.backend.entities.Usuario;

public interface UsuarioService {
    List<Usuario> listarTodo();
    Optional<Usuario> buscarPorId(Long id);
    Usuario guardar(Usuario usuario);
    //Optional<User> actualizar(UserRequest user, Long id);//revisar
    void eliminarPorId(Long id);

    boolean existeElUsername(String username);

    Page<Usuario> paginarTodo(Pageable pageable);

}
